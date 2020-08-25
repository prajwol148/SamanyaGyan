package SSJPrajwol.com;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundEffect {
    private Context context;
    private MediaPlayer mediaPlayer;

    public SoundEffect(Context context) {
        this.context = context;
    }

    public void setSound( int flag){
        switch (flag){
            
            case 1:
                int rightAnswer = R.raw.correct;
                playSound(rightAnswer);
                break;

            case 2:
                int wrongAnswer = R.raw.wrong;
                playSound(wrongAnswer);
                break;

            case 3:
                int startQuiz = R.raw.again;
                playSound(startQuiz);
                break;

        }
    }

    private void playSound(int audioFile) {
        mediaPlayer = MediaPlayer.create(context,audioFile);

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
    }

}
