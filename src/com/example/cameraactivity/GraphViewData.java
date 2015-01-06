package com.example.cameraactivity;

import com.jjoe64.graphview.GraphViewDataInterface;

public class GraphViewData implements GraphViewDataInterface {
   private  double x;
	private double y;

    public GraphViewData(double x, double y) {
        this.x = x;
        this.y = y;
    }

   
    
    @Override
    public double getX(){
    	return this.x;
    }
    @Override
    public double getY() {
        return this.y;
    }
}