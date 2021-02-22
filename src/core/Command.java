package core;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Command {
	
	protected Member sender;
	protected String name;
	protected String[] args;
	protected MessageReceivedEvent event;
	
	public Command(Member sender, String name, String[] args, MessageReceivedEvent event) {
		this.sender = sender;
		this.name = name;
		this.args = args;
		this.event = event;
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
	
	public MessageReceivedEvent getEvent() {
		return event;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}
	
}
