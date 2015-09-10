Loading Component View
======
**Loading Component View** is a customizable Android component for long background tasks.

With this component you can define several phases with associated frames and the component will be showing the frames.

The use is very easy: update the component with the name of the phase, the text to show and the percentage (referenced to 100).

You can also define the mode (blocking/not blocking). In **blocking mode**, the phases will show all frames even if the long task has finished. In **not blocking mode**, when a new phased arrives, the phase before is finished automatically.

In addition, you're allowed to customize the millis between frames and the default image (for phases without backgroundImages). All of this from the constructor of the handler.

##Screenshots

![Screenshot Android](https://github.com/juaagugui/LoadingComponentView/blob/master/art/screenShot1.png "screenshot1")
![Screenshot Android](https://github.com/juaagugui/LoadingComponentView/blob/master/art/screenShot2.png "screenshot2")  
![Screenshot Android](https://github.com/juaagugui/LoadingComponentView/blob/master/art/screenShot3.png "screenshot3")
![Screenshot Android](https://github.com/juaagugui/LoadingComponentView/blob/master/art/screenShot4.png "screenshot4")

## Version 
* Version 2.0

 * Integration with Android Studio structure

 * Refactoring resources and packages

## How-to use this code

###Implementation
 * Define the **view** in your .xml layout. For example:

		<app.juaagugui.loadingcomponent.views.LoadingComponentView
	        android:id="@+id/loading_component_view"
	        android:layout_width="400dp"
	        android:layout_height="400dp"
	        android:layout_centerHorizontal="true"
	        android:layout_centerVertical="true" />

* The handler receives a Map<String, LinkedList<Integer>> where key are the name of phases and, the list of Integers, the frames for this phase. For example:

		private Map<String, LinkedList<Integer>> getAnimations() {
			Map<String, LinkedList<Integer>> retMap = new HashMap<String, LinkedList<Integer>>();
			LinkedList<Integer> tempResourceList = new LinkedList<Integer>();
			//This phase only displays the default frame
			retMap.put("PHASE1, null);
	
			tempResourceList = new LinkedList<Integer>();
			tempResourceList.add(R.drawable.scene01);
			tempResourceList.add(R.drawable.scene02);
			
			//This phase has two frames
			retMap.put("PHASE2", tempResourceList);
	
			retMap.put("PHASE3", null);
		return retMap;
	   } 
* The handler receives an ILoadingComponentLifecycleListener for things to do before and after all phases (for example, hide/show other views etc.)

* In your activity/fragment, define a handler (**LoadingComponentViewHandler**) which will notify the updates to the view. For example:

		loadingComponentView = (LoadingComponentView) rootView.findViewById(R.id.loading_component_view);
		//"this" implements ILoadingComponentLifecycleListener
		loadingComponentViewHandler = new LoadingComponentViewHandler(loadingComponentView, getAnimations(), this);
		
###Use
* Whenever you want to update the animation, just call the **update** method of the defined handler:

	//This will show frames for phase 1, the text "Running phase 1" 
	//and the progress of the progressBar will be 0%
	
	context.updateProgressBar("PHASE1", "Running phase 1", 0); 

* To finish animations, call the updateMethod with percentage > 100%, or call finishComponent().

## License 
* see [LICENSE](https://github.com/juaagugui/LoadingComponentView/blob/master/LICENSE) file

## Contact
#### Developer/Company
* e-mail: juan.aguilar.guisado@gmail.com
* Twitter: [@juaagugui](https://twitter.com/juaagugui)
* Linkedin: [Juan Aguilar Guisado](http://es.linkedin.com/in/juanaguilarguisado)