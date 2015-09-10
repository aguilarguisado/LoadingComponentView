package app.juaagugui.loadingcomponent.views;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import app.juaagugui.loadingcomponent.R;
import app.juaagugui.loadingcomponent.listeners.ILoadingComponentLifecycleListener;
import app.juaagugui.loadingcomponent.utils.Cuarter;


/**
 * @author Juan Aguilar Guisado
 * @since 1.0
 */
public class LoadingComponentView extends RelativeLayout implements ILoadingComponentView {


    private Integer millisBetweenFrames;

    private ProgressBar progressBarView;
    private TextView loadingTextView;
    private ImageView backgroundImageView;
    private Boolean blockingMode;

    private int defaultImageResource;
    private String defaultLoadingText;

    private ILoadingComponentLifecycleListener lifecycleListener;

    private List<Cuarter<String, Queue<Integer>, String, Integer>> phases;

    private Boolean isFinished;

    public LoadingComponentView(Context context) {
        super(context);
        init(context);
    }

    public LoadingComponentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingComponentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View container = inflater.inflate(R.layout.layout_loading_component, this, true);
        View imageContainer = container.findViewById(R.id.backgroundImageContainer);
        backgroundImageView = (ImageView) imageContainer.findViewById(R.id.backgroundImage);
        progressBarView = (ProgressBar) container.findViewById(R.id.progress_bar_indicator);
        loadingTextView = (TextView) container.findViewById(R.id.loading_textview);
        blockingMode = false;
        millisBetweenFrames = 500;
        defaultImageResource = R.drawable.ic_launcher;
        defaultLoadingText = "Loading...";
        isFinished = true;
    }

    @Override
    public int getProgressPercentage() {
        if (progressBarView == null) {
            return 0;
        }
        return progressBarView.getProgress();
    }

    @Override
    public void setProgressPercentage(int progress) {
        progressBarView.setProgress(progress);
        if (progress > 100) {
            onFinishComponent();
        } else if (isFinished) {
            isFinished = false;
            onStartComponent();
        }
    }

    @Override
    public void setLoadingText(String text) {
        if (text != null)
            loadingTextView.setText(text);
        else
            loadingTextView.setText(defaultLoadingText);

    }

    @Override
    public void setDefaultFrameMillis(Integer millis) {
        millisBetweenFrames = millis;
    }

    @Override
    public void setBlockingMode(Boolean isBlockingMode) {
        this.blockingMode = isBlockingMode;
    }

    @Override
    public void setDefaultImageBackground(Integer imageResource) {
        this.defaultImageResource = imageResource;
    }

    @Override
    public void setDefaultLoadingText(String defaultText) {
        this.defaultLoadingText = defaultText;
    }

    @Override
    public void addPhase(String phase, Queue<Integer> listImages, String loadingText, Integer progress) {
        if (listImages != null) {
            getPhases().add(new Cuarter<String, Queue<Integer>, String, Integer>(phase, listImages, loadingText, progress));

        } else {
            LinkedList<Integer> defaultList = new LinkedList<Integer>();
            defaultList.add(defaultImageResource);
            getPhases().add(new Cuarter<String, Queue<Integer>, String, Integer>(phase, defaultList, loadingText, progress));
        }

        if (blockingMode) {
            if (getPhases().size() == 1) {
                nextPhase();
            }
        } else {
            if (getPhases().size() == 1) {
                nextPhase();
            } else {
                getPhases().remove(0);
                nextPhase();
            }
        }
    }

    @Override
    public void onStartComponent() {
        setVisibility(LoadingComponentView.VISIBLE);
        progressBarView.setVisibility(ProgressBar.VISIBLE);
        if (lifecycleListener != null) {
            lifecycleListener.onStartComponent();
        }
    }

    @Override
    public void onFinishComponent() {
        isFinished = true;
        setVisibility(LoadingComponentView.GONE);
        progressBarView.setVisibility(ProgressBar.GONE);
        if (lifecycleListener != null) {
            lifecycleListener.onFinishComponent();
        }
    }

    /**
     * @return Current displaying phase
     */
    private Cuarter<String, Queue<Integer>, String, Integer> getCurrentPhase() {
        Cuarter<String, Queue<Integer>, String, Integer> phaseName = new Cuarter<String, Queue<Integer>, String, Integer>(null, null, null, null);
        if (!getPhases().isEmpty())
            phaseName = getPhases().get(0);
        return phaseName;
    }

    /**
     * Method to go to the next phase
     */
    private void nextPhase() {
        if (getPhases().size() > 0) {
            Cuarter<String, Queue<Integer>, String, Integer> currentPhase = getPhases().get(0);
            setLoadingText(currentPhase.getThird());
            setProgressPercentage(currentPhase.getFourth());
            nextFrame(currentPhase.getFirst());
        }
    }

    /**
     * Method to go to the next frame inside a phase
     *
     * @param phaseName Current phase name
     */

    private void nextFrame(final String phaseName) {

        Cuarter<String, Queue<Integer>, String, Integer> phaseBefore = getCurrentPhase();

        Integer image = phaseBefore.getSecond().poll();
        if (image == null) {
            getPhases().remove(0);
            if (getPhases().size() != 0) {
                nextPhase();
            }
        } else {
            backgroundImageView.setBackgroundResource(image);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                public void run() {
                    if (phaseName.equals(getCurrentPhase().getFirst())) {
                        nextFrame(phaseName);
                    }
                }
            }, millisBetweenFrames);
        }

    }

    /**
     * @return Phases not displayed yet
     */
    synchronized private List<Cuarter<String, Queue<Integer>, String, Integer>> getPhases() {
        if (phases == null) {
            phases = new LinkedList<Cuarter<String, Queue<Integer>, String, Integer>>();
        }
        return phases;
    }

    @Override
    public void setComponentLifecycleListener(ILoadingComponentLifecycleListener listener) {
        this.lifecycleListener = listener;
    }

    @Override
    public TextView getLoadingTextView() {
        return loadingTextView;
    }
}
