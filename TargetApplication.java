
public class TargetApplication {
	private BackEnd backEnd;
	private TargetGUI gui;

	public TargetApplication() {
		this.backEnd = new BackEnd();
		this.gui = new TargetGUI(this.backEnd);
		
	}
	
	public static void main(String[] args) {
		TargetApplication app = new TargetApplication();
	}
}
