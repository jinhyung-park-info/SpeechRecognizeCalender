package com.naver.hackday.android.speechrecognizecalender.src.network.clova;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.naver.hackday.android.speechrecognizecalender.R;
import com.naver.hackday.android.speechrecognizecalender.src.common.util.AudioWriterPCM;
import com.naver.speech.clientapi.SpeechRecognitionResult;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.List;

import static com.naver.hackday.android.speechrecognizecalender.src.ApplicationClass.getApplicationClassContext;
import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.CLIENT_ID;

public class ClovaViewModel extends ViewModel {

    public ObservableField<String> mRecognizerStatus = new ObservableField<>("Clova Start");
    public ObservableField<String> mRecognizedResult = new ObservableField<>("");

    public ObservableField<Boolean> mBtnEnable = new ObservableField<>(true);

    private NaverRecognizer naverRecognizer;
    private AudioWriterPCM writer;

    public ClovaViewModel() {
        RecognitionHandler handler = new RecognitionHandler(this);
        naverRecognizer = new NaverRecognizer(getApplicationClassContext(), handler, CLIENT_ID);

        // NOTE : initialize() must be called on start time.
        naverRecognizer.getSpeechRecognizer().initialize();
    }

    public void clovaClicked() {
        if (!naverRecognizer.getSpeechRecognizer().isRunning()) {
            // Start button is pushed when SpeechRecognizer's state is inactive.
            // Run SpeechRecongizer by calling recognize().
            mRecognizedResult.set("Connecting...");
            mRecognizerStatus.set("인식 정지");
            naverRecognizer.recognize();
        } else {
            mBtnEnable.set(false);

            naverRecognizer.getSpeechRecognizer().stop();
        }
    }

    static class RecognitionHandler extends Handler {
        private final WeakReference<ClovaViewModel> mViewModel;

        RecognitionHandler(ClovaViewModel clovaViewModel) {
            mViewModel = new WeakReference<ClovaViewModel>(clovaViewModel);
        }

        @Override
        public void handleMessage(@NotNull Message msg) {
            ClovaViewModel clovaViewModel = mViewModel.get();
            if (clovaViewModel != null) {
                clovaViewModel.handleMessage(msg);
            }
        }
    }

    // Handle speech recognition Messages.
    private void handleMessage(Message msg) {
        switch (msg.what) {
            case R.id.clientReady:
                // Now an user can speak.
                mRecognizedResult.set("Connected...");
                writer = new AudioWriterPCM(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/NaverSpeechTest");
                writer.open("NaverSpeechTest");
                break;

            case R.id.audioRecording:
                writer.write((short[]) msg.obj);
                break;

            case R.id.partialResult:
                // Extract obj property typed with String.
                String mResult = (String) (msg.obj);
                mRecognizedResult.set(mResult);
                break;

            case R.id.finalResult:
                // Extract obj property typed with String array.
                // The first element is recognition result for speech.
                SpeechRecognitionResult speechRecognitionResult = (SpeechRecognitionResult) msg.obj;
                List<String> results = speechRecognitionResult.getResults();
                StringBuilder strBuf = new StringBuilder();
                for (String result : results) {
                    strBuf.append(result);
                    strBuf.append("\n");
                }
                //                txtResult.setText(mResult);
                mResult = strBuf.toString();
                mRecognizedResult.set(mResult);
                break;

            case R.id.recognitionError:
                if (writer != null) {
                    writer.close();
                }
                mResult = "Error code : " + msg.obj.toString();
                mRecognizedResult.set(mResult);
                mRecognizerStatus.set("인식 시작");
                mBtnEnable.set(true);

                break;

            case R.id.clientInactive:
                if (writer != null) {
                    writer.close();
                }
                mRecognizerStatus.set("인식 시작");
                mBtnEnable.set(true);
                break;
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        naverRecognizer.getSpeechRecognizer().release();
    }
}

