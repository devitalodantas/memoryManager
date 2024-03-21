package so;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Process {
	private int id;
	private int sizeInMemory;
	private int timeToExecute;
	private Priority priority;
	
	public Process(int id, int sizeInMemory) {
		this.id = id;
		this.sizeInMemory = sizeInMemory;
		//List<Integer> givenList = Arrays.asList(1,2,4,5,8,10,20,50,100);
		//this.sizeInMemory = givenList.get(rand.nextInt(givenList.size()));
		//this.timeToExecute = timeToExecute;
		//this.priority = priority;
	}

	public int getSizeInMemory() {
		return sizeInMemory;
	}

	public void setSizeInMemory(int sizeInMemory) {
		this.sizeInMemory = sizeInMemory;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	
}
