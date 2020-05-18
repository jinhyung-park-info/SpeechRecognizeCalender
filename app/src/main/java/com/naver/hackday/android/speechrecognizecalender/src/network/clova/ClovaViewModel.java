package com.naver.hackday.android.speechrecognizecalender.src.network.clova;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.naver.hackday.android.speechrecognizecalender.R;
import com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants;
import com.naver.hackday.android.speechrecognizecalender.src.common.util.AudioWriterPCM;
import com.naver.speech.clientapi.SpeechRecognitionResult;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.List;

import static com.naver.hackday.android.speechrecognizecalender.src.ApplicationClass.getApplicationClassContext;
import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.CLOVA_CLIENT_ID;
import static com.naver.hackday.android.speechrecognizecalender.src.common.util.Util.isEmulator;

public class ClovaViewModel extends ViewModel {

    public ObservableField<String> mRecognizerStatus = new ObservableField<>("Clova Start");
    public ObservableField<String> mRecognizedResult = new ObservableField<>("");
    public ObservableField<Boolean> mBtnEnable = new ObservableField<>(true);

    public MutableLiveData<String> mRecognizedString;

    private NaverRecognizer naverRecognizer;
    private AudioWriterPCM writer;

    public ClovaViewModel() {
        try {
//            if (!isEmulator()) {
                RecognitionHandler handler = new RecognitionHandler(this);
                naverRecognizer = new NaverRecognizer(getApplicationClassContext(), handler, CLOVA_CLIENT_ID);

                // NOTE : initialize() must be called on start time.
                naverRecognizer.getSpeechRecognizer().initialize();
//            }
//            else{
//                mRecognizerStatus.set("에뮬레이터에서는 Clova 이용이 불가능합니다");
//                mBtnEnable.set(false);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mRecognizedString = new MutableLiveData<String>();
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

    public void clickTest(){
        mRecognizedString.setValue("내일 오후3시"); //5-20
        mRecognizedString.setValue("오늘 7시"); // 5-19
        mRecognizedString.setValue("내일모래 오후 3시"); //5-21
        mRecognizedString.setValue("일요일"); // ?
        mRecognizedString.setValue("다음 주 수요일"); //
        mRecognizedString.setValue("이번 주 토요일");
        mRecognizedString.setValue("6월 5일");
        mRecognizedString.setValue("12월 15일");
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
                mRecognizedString.setValue(results.get(0));
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

