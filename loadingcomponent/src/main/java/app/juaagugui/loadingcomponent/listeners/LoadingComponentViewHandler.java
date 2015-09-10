package app.juaagugui.loadingcomponent.listeners;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import app.juaagugui.loadingcomponent.views.ILoadingComponentView;
import app.juaagugui.loadingcomponent.views.LoadingComponentView;

import java.util.LinkedList;
import java.util.Map;


/**
 * Class which will update the component handling update events.
 * 
 * @author Juan Aguilar Guisado
 * @since 1.0
 */
public final class LoadingComponentViewHandler extends Handler implements ILoadingComponentViewHandler {

	private ILoadingComponentView progressLayout;
	private Map<String, LinkedList<Integer>> animations;

	/**
	 * 
	 * @param progressLayout
	 *            Loading component view from the activity
	 * @param initialContent
	 *            Map<String,List<Integer>> (pahse name, list resources) with
	 *            the phases and the frames for each phase
	 */
	public LoadingComponentViewHandler(LoadingComponentView progressLayout, Map<String, LinkedList<Integer>> initialContent) {
		this(progressLayout, initialContent, null, null, null, null, null);
	}

	/**
	 * 
	 * @param progressLayout
	 *            Loading component view from the activity
	 * @param initialContent
	 *            Map<String,List<Integer>> (pahse name, list resources) with
	 *            the phases and the frames for each phase
	 * @param listener
	 *            {@link ILoadingComponentLifecycleListener} Listener for
	 *            executing custom start/end of animations.
	 */
	public LoadingComponentViewHandler(LoadingComponentView progressLayout, Map<String, LinkedList<Integer>> initialContent, ILoadingComponentLifecycleListener listener) {
		this(progressLayout, initialContent, listener, null, null, null, null);
	}

	/**
	 * 
	 *
	 * @param progressLayout
	 *            Loading component view from the activity
	 * 
	 * @param initialContent
	 *            Map<String,List<Integer>> (phase name, list resources) with
	 *            the phases and the frames for each phase.
	 * 
	 *            * @param listener {@link ILoadingComponentLifecycleListener}
	 *            Listener for executing custom start/end of animations.
	 * 
	 * @param waitPrePhase
	 *            Boolean to indicate if one phase must wait for the phase
	 *            before to be finished to start its frames. If it's null,
	 *            defaultValue is set
	 * 
	 * @param defaultLoadingText
	 *            Set the default text for the Textview. Default: "Loading..."
	 *            If it's null, defaultValue is set
	 * 
	 * 
	 * @param defaultBackGroundImage
	 *            Set the default frame in case a phase has not images.
	 * 
	 *            If it's null, defaultValue is set
	 * 
	 * @param frameMillis
	 *            Set the default time between frame. Default: 500 millis. If
	 *            it's null, defaultValue is set
	 */
	public LoadingComponentViewHandler(LoadingComponentView progressLayout, Map<String, LinkedList<Integer>> initialContent, ILoadingComponentLifecycleListener listener, Boolean waitPrePhase,
			String defaultLoadingText, Integer defaultBackGroundImage, Integer frameMillis) {
		this.progressLayout = progressLayout;

		if (initialContent == null) {
			throw new IllegalArgumentException("The map with animation phases and images is null!!");
		}

		this.animations = initialContent;

		if (waitPrePhase != null) {
			this.progressLayout.setBlockingMode(waitPrePhase);
		}

		if (defaultLoadingText != null) {
			this.progressLayout.setDefaultLoadingText(defaultLoadingText);
		}

		if (defaultBackGroundImage != null) {
			this.progressLayout.setDefaultImageBackground(defaultBackGroundImage);
		}

		if (frameMillis != null) {
			this.progressLayout.setDefaultFrameMillis(frameMillis);
		}

		if (listener != null) {
			this.progressLayout.setComponentLifecycleListener(listener);
		}
	}

	@Override
	public void update(String phaseName, String message, int status) {
		Message m = new Message();
		Bundle bundle = new Bundle();
		bundle.putInt("status", status);
		bundle.putString("message", message);
		bundle.putString("phase", phaseName);
		m.setData(bundle);
		super.sendMessage(m);
	}

	@Override
	public void finishComponent() {
		Message m = new Message();
		Bundle bundle = new Bundle();
		bundle.putBoolean("finishAnimations", true);
		m.setData(bundle);
		super.sendMessage(m);
	}

	public void handleMessage(Message msg) {
		Bundle b = msg.getData();
		Boolean isFinishMessage = b.getBoolean("finishAnimations");
		if (isFinishMessage != null && isFinishMessage) {
			progressLayout.onFinishComponent();
		} else {

			Integer status = b.getInt("status", progressLayout.getProgressPercentage());
			String message = b.getString("message", "");
			String phase = b.getString("phase", "Loading");
			if (status < 0) {
				status = 0;
			}

			LinkedList<Integer> frames = null;
			LinkedList<Integer> images = animations.get(phase);
			if (images != null) {
				frames = new LinkedList<Integer>(images);
			}

			progressLayout.addPhase(phase, frames, message, status);
		}
	}
}
