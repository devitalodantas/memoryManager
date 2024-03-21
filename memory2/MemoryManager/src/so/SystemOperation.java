package so;

import so.cpu.CpuManager;
import so.memory.MemoryManager;
import so.memory.Strategy;
import so.schedule.Schedule;
import so.Process;

public class SystemOperation {
	private static MemoryManager memoryManager;
	private static CpuManager cpuManager;
	private static Schedule schedule;
	
	
	private static void createProcess(int strategy) {
		
		if(memoryManager == null) {
			
			if(strategy == 1) {
				memoryManager = new MemoryManager(Strategy.FIRST_FIT);
			}else if(strategy == 2) {
				memoryManager = new MemoryManager(Strategy.BEST_FIT);
			}else if(strategy == 3) {
				memoryManager = new MemoryManager(Strategy.WORST_FIT);
			}
		}
		
		if(cpuManager == null) {
			cpuManager = new CpuManager();
		}
	}
	
	public static Process systemCall(SystemCallType type, Process p, int sizeInMemory, int strategy, int id) {
		
		if(type.equals(SystemCallType.WRITE)) {
			boolean fitInMemory = memoryManager.writeProcess(p);
			if(fitInMemory) {
				System.out.println("Processo de ID " + id + " alocado com sucesso!");
			}else {
				System.out.println("Não há espaço na memória para alocação do processo!");
			}
		}else if(type.equals(SystemCallType.CREATE)) {
			createProcess(strategy);
			return new Process(id, sizeInMemory);
		}else if(type.equals(SystemCallType.DELETE)) {
			//memoryManager.deleteProcess(p);
		}
		return null;
	}
	
	public static void deleteProcess(MemoryManager mm, int id) {
		 boolean processFound = false;
	        int[] physicalMemory = mm.getPhysicMemory();
	        for (int i = 0; i < physicalMemory.length; i++) {
	            if (physicalMemory[i] == id) {
	                mm.deleteProcess(new Process(id, 0)); // Criamos um novo processo apenas com o ID
	                processFound = true;
	                System.out.println("Processo deletado com sucesso!");
	                break; 
	            }
	        }
	        
	        //Caso o processo não seja encontrado
	        if (!processFound) {
	            System.out.println("Processo não encontrado. Tente novamente!");
	        }
	}
	public static MemoryManager getMemoryManager() {
		return memoryManager;
	}

	public static void setMemoryManager(MemoryManager memoryManager) {
		SystemOperation.memoryManager = memoryManager;
	}

	
	
}
