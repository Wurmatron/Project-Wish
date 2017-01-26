package wish.wurmatron.common.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wish.wurmatron.api.Global;
import wish.wurmatron.common.config.Settings;

/**
	* Used insted of System.out.println() to allow for easier viewing for specific mod output text.
	*/
public class LogHandler {

		private static final Logger logger = LogManager.getLogger(Global.NAME);

		/**
			* @param level   Type of message to output.
			* @param message Message that you want to send to console.
			*/
		public static void log(Level level, String message) {
				logger.log(level, message);
		}

		/** @param message message to output **/
		public static void info(String message) {
				log(Level.INFO, message);
		}

		/** @param message message to output **/
		public static void warn(String message) {
				log(Level.WARN, message);
		}

		/**
			* Notice: this method will only output if the mods debug mode is enabled.
			*
			* @param message message to output
			*/
		public static void debug(String message) {
				if (Settings.debug) log(Level.DEBUG, message);
		}
}
