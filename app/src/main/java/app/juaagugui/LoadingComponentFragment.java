package app.juaagugui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import app.juaagugui.loadingcomponent.listeners.ILoadingComponentLifecycleListener;
import app.juaagugui.loadingcomponent.listeners.ILoadingComponentViewHandler;
import app.juaagugui.loadingcomponent.listeners.LoadingComponentViewHandler;
import app.juaagugui.loadingcomponent.views.LoadingComponentView;

/**
 * 
 * @author Juan Aguilar Guisado
 * 
 */

public class LoadingComponentFragment extends Fragment implements ILoadingComponentLifecycleListener, OnClickListener {

	public static enum PHASES {
		P1, P2, P3, P4
	}

	private LoadingComponentView loadingComponentView;
	private ILoadingComponentViewHandler loadingComponentViewHandler;
	private LinearLayout buttonContainer;
	private LongTaskSampleSimulator taskSimulator;

	public LoadingComponentFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_loading_component_view, container, false);
		loadingComponentView = (LoadingComponentView) rootView.findViewById(R.id.loading_component_view);
		loadingComponentViewHandler = new LoadingComponentViewHandler(loadingComponentView, getAnimations(), this);
		buttonContainer = (LinearLayout) rootView.findViewById(R.id.buttonContainer);

		Button blockingButton = (Button) buttonContainer.findViewById(R.id.blockingButton);
		Button nonBlockingButton = (Button) buttonContainer.findViewById(R.id.nonBlockingButton);
		blockingButton.setOnClickListener(this);
		nonBlockingButton.setOnClickListener(this);

		taskSimulator = new LongTaskSampleSimulator(this);
		return rootView;
	}

	public void updateProgressBar(String phaseName, String message, int status) {
		loadingComponentViewHandler.update(phaseName, message, status);
	}

	private Map<String, LinkedList<Integer>> getAnimations() {
		Map<String, LinkedList<Integer>> retMap = new HashMap<String, LinkedList<Integer>>();
		LinkedList<Integer> tempResourceList = new LinkedList<Integer>();
		retMap.put(PHASES.P1.toString(), null);

		tempResourceList = new LinkedList<Integer>();
		tempResourceList.add(R.drawable.scene01);
		tempResourceList.add(R.drawable.scene02);
		tempResourceList.add(R.drawable.scene03);
		tempResourceList.add(R.drawable.scene04);
		tempResourceList.add(R.drawable.scene05);
		tempResourceList.add(R.drawable.scene06);
		tempResourceList.add(R.drawable.scene07);
		retMap.put(PHASES.P2.toString(), tempResourceList);

		tempResourceList = new LinkedList<Integer>();
		tempResourceList.add(R.drawable.scene08);
		tempResourceList.add(R.drawable.scene09);
		tempResourceList.add(R.drawable.scene10);
		tempResourceList.add(R.drawable.scene11);
		tempResourceList.add(R.drawable.scene12);
		tempResourceList.add(R.drawable.scene13);
		retMap.put(PHASES.P3.toString(), tempResourceList);

		tempResourceList = new LinkedList<Integer>();
		tempResourceList.add(R.drawable.scene14);
		tempResourceList.add(R.drawable.scene15);
		tempResourceList.add(R.drawable.scene16);
		tempResourceList.add(R.drawable.scene17);
		tempResourceList.add(R.drawable.scene18);
		tempResourceList.add(R.drawable.scene19);
		tempResourceList.add(R.drawable.scene20);
		retMap.put(PHASES.P4.toString(), tempResourceList);

		return retMap;
	}

	private class LongTaskSampleSimulator {

		LoadingComponentFragment context;

		public LongTaskSampleSimulator(LoadingComponentFragment context) {
			this.context = context;
		}

		public void task1() {
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {

				public void run() {
					context.updateProgressBar(PHASES.P1.toString(), "Running phase 2", 20);
					task2();
				}
			}, 1000);
		}

		public void task2() {
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {

				public void run() {
					context.updateProgressBar(PHASES.P2.toString(), "Running phase 3", 40);
					task3();
				}
			}, 1500);
		}

		public void task3() {
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {

				public void run() {
					context.updateProgressBar(PHASES.P3.toString(), "Running phase 4", 60);
					task4();
				}
			}, 1500);
		}

		public void task4() {
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {

				public void run() {
					context.updateProgressBar(PHASES.P4.toString(), "Running phase 5", 80);
					task5();
				}
			}, 1500);
		}

		public void task5() {
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {

				public void run() {
					context.updateProgressBar("", "Loaded", 100);
					task6();
				}
			}, 1500);
		}

		public void task6() {

			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {

				public void run() {
					context.updateProgressBar("", "Loaded", 101);
				}
			}, 1500);
		}
	}

	@Override
	public void onStartComponent() {
		buttonContainer.setVisibility(LinearLayout.GONE);
	}

	@Override
	public void onFinishComponent() {
		buttonContainer.setVisibility(LinearLayout.VISIBLE);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.blockingButton) {
			blockingButtonClicked();
		} else if (v.getId() == R.id.nonBlockingButton) {
			nonBlockingButtonClicked();
		}
	}

	public void blockingButtonClicked() {
		loadingComponentView.setBlockingMode(true);
		taskSimulator.task1();
	}

	public void nonBlockingButtonClicked() {
		loadingComponentView.setBlockingMode(false);
		taskSimulator.task1();
	}

}
