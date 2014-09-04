package mss.loadingComponentView.views;

import java.util.Queue;

import mss.loadingComponentView.listeners.ILoadingComponentLifecycleListener;
import android.widget.TextView;

/**
 * 
 * @author Juan Aguilar Guisado
 * @version 1.0
 * @since 1.0
 * 
 */
public interface ILoadingComponentView {

	/**
	 * Adds a new phase to display (with its list of frames etc.)
	 * 
	 * @param phase
	 *            Name of the added phase
	 * @param listImages
	 *            List of resources (drawable id's) for each frame of the phase
	 * @param loadingText
	 *            Message for the added phase
	 * @param progress
	 *            Percentage of the added phase
	 */
	public void addPhase(String phase, Queue<Integer> listImages,
			String loadingText, Integer progress);

	/**
	 * Gets the percentage of the current phase
	 * 
	 * @return current percentage
	 */
	public int getProgressPercentage();

	/**
	 * Sets current percentage for the current phase
	 * 
	 * @param progress
	 *            new percentage
	 */

	public void setProgressPercentage(int progress);

	/**
	 * Sets the loading text for the current phase
	 * 
	 * @param text
	 *            new loading text
	 */
	public void setLoadingText(String text);

	/**
	 * @param isBlockingMode
	 *            if isBlockingMode = true, next phase must wait for the
	 *            previous phases to begin.
	 */
	public void setBlockingMode(Boolean isBlockingMode);

	/**
	 * Resume or initialize component behavior
	 */
	public void onStartComponent();

	/**
	 * Finish component behavior
	 */

	public void onFinishComponent();

	/**
	 * Sets default image resource for frames in a phase
	 * 
	 * @param imageResource
	 */

	public void setDefaultImageBackground(Integer imageResource);

	/**
	 * Sets default loading text for a phase
	 * 
	 * @param defaultText
	 */

	public void setDefaultLoadingText(String defaultText);

	/**
	 * Sets millis between each frame in a phase
	 * 
	 * @param millis
	 */
	public void setDefaultFrameMillis(Integer millis);

	/**
	 * Sets the listener to customize the behavior of the component when starts
	 * and finishes
	 * 
	 * @param listener
	 */
	public void setComponentLifecycleListener(
			ILoadingComponentLifecycleListener listener);

	/**
	 * 
	 * @return TextView subelement to customize
	 */
	public TextView getLoadingTextView();

}
