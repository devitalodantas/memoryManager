package so.memory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import so.Process;

public class MemoryManager {
	private static int[] physicMemory;
	private Strategy strategy;
	private static List<Process> registeredProcesses = new ArrayList<>();
	
	public MemoryManager(Strategy strategy) {
		this.strategy = strategy;
		this.physicMemory = new int[128];
	}
	
	public boolean writeProcess(Process p) {
		boolean allocated = true;
		
		if(this.strategy.equals(Strategy.FIRST_FIT)) {
			allocated = this.writeUsingFirstFit(p);
		}else if(this.strategy.equals(Strategy.BEST_FIT)){
			allocated = this.writeUsingBestFit(p);
		}else if(this.strategy.equals(Strategy.WORST_FIT)) {
			allocated = this.writeUsingWorstFit(p);
		}
		
		if(allocated) {
			registeredProcesses.add(p);
		}
		
		return allocated;
	}
	
	/*
	 * Worst Fit busca o maior bloco livre disponível e aloca o processo a partir do index que 
	 * se inicia esse bloco.
	 */
	private boolean writeUsingWorstFit(Process p) {
		boolean oneProcessFit = false;
        int countReal = 0;
        int indice_bestFit = 0;

        for (int i = physicMemory.length - 1; i >= 0; i--) {
            boolean processFitsInSpace = true;
            if (physicMemory[i] == 0 && (i - p.getSizeInMemory() + 1) >= 0) {
                for (int j = i; j > i - p.getSizeInMemory(); j--)
                    if (physicMemory[j] != 0) {
                        processFitsInSpace = false;
                        break;
                    }

                if (processFitsInSpace) {
                    int countR = 0;
                    int countL = 0;

                    while ((i + countR) < physicMemory.length && physicMemory[i + countR] == 0)
                        countR++;

                    while ((i - p.getSizeInMemory() - countL) >= 0 && physicMemory[i - p.getSizeInMemory() - countL] == 0)
                        countL++;

                    int totalSpace = countR + countL;

                    if (totalSpace >= countReal) {
                        countReal = totalSpace;
                        indice_bestFit = i;
                        oneProcessFit = true;
                    }
                }
            }
        }
        
        if (oneProcessFit)
            for (int k = indice_bestFit; k > indice_bestFit - p.getSizeInMemory(); k--)
                physicMemory[k] = p.getId();
        return oneProcessFit;
	}
	
	/*
	 * O algoritmo BEST FIT percorre toda memória, procurando blocos livres.
	 * Registra o menor bloco livre que é grande o suficiente para alocar o processo.
	 */
	private boolean writeUsingBestFit(Process p) {
		 boolean oneProcessFit = false;
	        int count = physicMemory.length;
	        int indexBest = 0;

	        for (int i = physicMemory.length - 1; i >= 0; i--) {
	            boolean processFitsInSpace = true;
	            if (physicMemory[i] == 0 && (i - p.getSizeInMemory() + 1) >= 0) {
	                for (int j = i; j > i - p.getSizeInMemory(); j--)
	                    if (physicMemory[j] != 0) {
	                        processFitsInSpace = false;
	                        break;
	                    }

	                if (processFitsInSpace) {
	                    int countR = 0;
	                    int countL = 0;

	                    while ((i + countR) < physicMemory.length && physicMemory[i + countR] == 0)
	                        countR++;

	                    while ((i - p.getSizeInMemory() - countL) >= 0 && physicMemory[i - p.getSizeInMemory() - countL] == 0)
	                        countL++;

	                    int totalSpace = countR + countL;

	                    if (totalSpace <= count) {
	                        count = totalSpace;
	                        indexBest = i;
	                        oneProcessFit = true;
	                    }
	                }
	            }

	        }

	        if (oneProcessFit)
	            for (int k = indexBest; k > indexBest - p.getSizeInMemory(); k--)
	                physicMemory[k] = p.getId();

	        return oneProcessFit;
	    
	}
	
	/*O método FIRST FIT aloca o processo no primeiro bloco com espaço suficiente para ele.
	 */
	public boolean writeUsingFirstFit(Process p) {
        boolean allocated = false;
        for (int i = 0; i < physicMemory.length; i++) {
            if (physicMemory[i] == 0) {
                int spaceAvailable = 0;
                for (int j = i; j < physicMemory.length && physicMemory[j] == 0; j++) {
                    spaceAvailable++;
                }
                
                if (spaceAvailable >= p.getSizeInMemory()) {
                    for (int k = i; k < i + p.getSizeInMemory(); k++) {
                    	physicMemory[k] = p.getId();
                    }
                    allocated = true;
                    break;
                }
            }
        }
        return allocated;
    }
	
	public void showMemory() {
		for(int i = 0; i < physicMemory.length; i++) {
			if(i % 10 == 0) {
				System.out.println();
			}
			System.out.print(physicMemory[i] + "  ,  ");
		}
		System.out.println();
	}
	
	public static void deleteProcess(Process p) {
		int id = p.getId();
        for (int i = 0; i < physicMemory.length; i++)
            if (physicMemory[i] == id)
                physicMemory[i] = 0;

        registeredProcesses.removeIf(process -> process.getId() == id);
    }

	public int[] getPhysicMemory() {
		return physicMemory;
	}

	public void setPhysicMemory(int[] physicMemory) {
		this.physicMemory = physicMemory;
	}
	
	
	
}
