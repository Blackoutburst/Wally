package core;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

public class Command {
	
	protected Member sender;
	protected String name;
	protected String[] args;
	protected MessageChannel channel;
	protected Message message;
	
	public Command(Member sender, String name, String[] args, MessageChannel channel, Message message) {
		this.sender = sender;
		this.name = name;
		this.args = args;
		this.channel = channel;
		this.message = message;
	}

	public Member getSender() {
		return sender;
	}

	public String getName() {
		return name;
	}

	public String[] getArgs() {
		return args;
	}
	
	public MessageChannel getChannel() {
		return channel;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public Message getMessage() {
		return message;
	}
	
}
