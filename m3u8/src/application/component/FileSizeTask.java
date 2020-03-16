package application.component;

import application.utils.FFMPEG;
import javafx.concurrent.Task;

public class FileSizeTask extends Task<Integer> {
	private String dir;

	public FileSizeTask(String dir) {
		this.dir = dir;
	}

	@Override
	protected Integer call() throws Exception {
		FFMPEG.merge(dir, this);
		return null;
	}

	public void updateFileSize(String value) {
		super.updateMessage(value);
	}

	public void done() {
		super.updateProgress(1, 1);
	}

}
