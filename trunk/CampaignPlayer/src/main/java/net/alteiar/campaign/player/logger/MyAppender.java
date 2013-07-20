package net.alteiar.campaign.player.logger;


import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

public class MyAppender extends AppenderSkeleton {

	@Override
	protected void append(LoggingEvent event) {
		Throwable throwable = null;
		if (event.getThrowableInformation() != null) {
			throwable = event.getThrowableInformation().getThrowable();
		}
		String msg = event.getMessage().toString();

		Level current = event.getLevel();
		if (Level.FATAL.equals(current)) {
			ExceptionTool.showError(throwable, msg);
		} else if (Level.ERROR.equals(current)) {
			ExceptionTool.showError(throwable, msg);
		} else {
			ExceptionTool.showWarning(throwable, msg);
		}
	}

	@Override
	public void close() {
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

}
