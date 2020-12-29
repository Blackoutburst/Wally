package commands;

import core.Lines;
import core.Reader;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Convert {
	
	private static int [] qScore = new int[] {110, 111, 112, 113, 114, 
			115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 
			125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 
			135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 
			145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 
			155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 
			165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 
			175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 
			185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 
			195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 
			205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 
			215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 
			225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 
			235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 
			245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 
			255, 256, 257, 258, 259, 260, 261, 262, 263, 264, 
			265, 266, 267, 268, 269, 270, 271, 272, 273, 274, 
			275, 276, 277, 278, 279, 280, 281, 282, 283, 284, 
			285, 286, 287, 288, 289, 290, 291, 292, 293, 294, 
			295, 296, 297, 298, 299, 300, 301, 302, 303, 304, 
			305, 306, 307, 308, 309, 310, 311, 312, 313, 314, 
			315, 316, 317, 318, 319, 320, 321, 322, 323, 324, 
			325, 326, 327, 328, 329, 330, 331, 332, 333, 334, 
			335, 336, 337, 338, 339, 340, 341, 342, 343, 344, 
			345, 346, 347, 348, 349, 350, 351, 352, 353, 354, 
			355, 356, 357, 358, 359, 360, 361, 362, 363, 364, 
			365, 366, 367, 368, 369, 370, 371, 372, 373, 374, 
			375, 376, 377, 378, 379, 380, 381, 382, 383, 384, 
			385, 386, 387, 388, 389, 390, 391, 392, 393, 394, 
			395, 396, 397, 398, 399, 400};
	
	/**
	 * Convert Finals score to Qualification score and
	 * Qualification score to Finals score
	 * @param event
	 * @uthor Blackoutburst
	 */
	public static void convert(MessageReceivedEvent event) {
		String[] args = event.getMessage().getContentDisplay().split(" ");
		
		if (args.length < 3) {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.bad_usage).replace("%command%", "!convert q/f [value]")).complete();
			return;
		}
		if (args[1].toLowerCase().equals("f")) {
			event.getChannel().sendMessage("Coming soon !").complete();return;
			/*
			double a = 0.00000124349d;
			double b = -0.00170458d;
			double c = 1.30982d;
			double d = -4.0731d;
			int F = 0;
			double Q = 0;
			
			try {
				F = Integer.valueOf(args[2]);
			} catch (Exception e) {event.getChannel().sendMessage("Values must be a valid number!").complete();return;}
			if (F < 110 || F > 400) {event.getChannel().sendMessage("Value must be between 110 and 400").complete();return;}
			
			Q = a * Math.pow(F, 3) + b * Math.pow(F, 2) + c * F + d;
			event.getChannel().sendMessage(+F+"F compares to **"+Math.round(Q)+"**Q").complete();
			*/
			
		}
		else if (args[1].toLowerCase().equals("q")) {
			double a = -0.0000036008d;
			double b = 0.00483095d;
			double c = -0.239935d;
			double d = 80.817d;
			int Q = 0;
			double F = 0;
			
			try {
				Q = Integer.valueOf(args[2]);
			} catch (Exception e) {
				event.getChannel().sendMessage("Values must be a valid number!").complete();
				return;
			}
			if (Q < 110 || Q > 400) {
				event.getChannel().sendMessage("Value must be between 110 and 400").complete();
				return;
			}
			
			F = a * Math.pow(Q, 3) + b * Math.pow(Q, 2) + c * Q + d;
			event.getChannel().sendMessage(+Q+"Q compares to **"+Math.round(F)+"**F").complete();
		} else {
			event.getChannel().sendMessage(event.getAuthor().getAsMention()+", "+Reader.read(Lines.bad_usage).replace("%command%", "!convert q/f [value]")).complete();
		}
	}
}
