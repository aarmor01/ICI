package es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.MsPacManInput;
import es.ucm.fdi.ici.fsm.Transition;

public class PPillTokenNcloseGhost implements Transition {

	
	public PPillTokenNcloseGhost() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput)in;
		
		// TODO Auto-generated method stub
		return input.canEat();
	}
	
	@Override
	public String toString() {
		return "PPill Token N close Ghost";
	}

}
