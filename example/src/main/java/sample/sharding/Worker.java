package sample.sharding;

import java.io.Serializable;

import akka.actor.AbstractActor;
import sample.sharding.Consumer.JobWrapper;

public class Worker extends AbstractActor {

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(JobWrapper.class, msg -> {
					System.out.println("Woker Process: " + msg);
					sender().tell(new TaskDone(((JobWrapper) msg)), getSelf());
				})
				.build();
	}

	public static class TaskDone implements Serializable {
		private static final long serialVersionUID = -5414439407575682893L;

		public final JobWrapper job;

		public TaskDone(JobWrapper job) {
			this.job = job;
		}
		
		@Override
		public String toString() {
			return "Done Task > " + job;
		}
	}

}
