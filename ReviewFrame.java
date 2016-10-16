package voxspell;

@SuppressWarnings("serial")
public class ReviewFrame extends QuizFrame {
	
	public ReviewFrame(){
		super("r");
		modifyGUI();
	}
	
	public void modifyGUI(){
		lblNewSpellingQuiz.setText("   Review Quiz   ");
		dtrpnStartANew.setText("This mode allows you to review the mistakes you have made.");
	}
}
