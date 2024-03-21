package so;

import java.util.Scanner;

import so.memory.MemoryManager;
import so.memory.Strategy;

public class Execute {
	
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		int generateID = 1;
		
		System.out.println("Qual a estratégia que será utilizada?"
				+ "\n1) FIRST-FIT"
				+ "\n2) BEST-FIT"
				+ "\n3) WORST-FIT");
		
		
		int strategy = input.nextInt();
		
		SystemOperation so = new SystemOperation(); //Inicialização do Sistema Operacional
		//Criação do processo a partir da estratégia repassada
		Process p = so.systemCall(SystemCallType.CREATE, null, 0, strategy, 0);
		MemoryManager mm = so.getMemoryManager();
		
		
		String menu = "\nSelecione a opção desejada:"
				+ "\n1) Leitura de memória"
				+ "\n2) Criar novo processo"
				+ "\n3) Deletar processo por ID"
				+ "\n4) Sair";
		
		
		int opcao = 0;
		do{
			System.out.println(menu);
			opcao = input.nextInt();
			
			if(opcao == 1) {
				//Listagem da memória
				mm.showMemory();
			}else if(opcao == 2) {
				System.out.println("Qual o tamanho do processo? ");
				int sizeInMemory = input.nextInt();
				Process process = new Process(generateID, sizeInMemory);
				//Alocagem do processo na memória
				SystemOperation.systemCall(SystemCallType.WRITE, process, sizeInMemory, strategy, generateID);
				generateID++;
				
			}else if(opcao == 3){
				//Deleta processo da memória
				 System.out.println("Qual processo deseja deletar?\n");
                 int processId = input.nextInt();
                 so.deleteProcess(mm, processId);
				
			}else if(opcao == 4) {
				System.out.println("Sistema encerrado!");
			}
			else {
				System.out.println("Opção inválida.");
			}
		}while(opcao != 4);
	}
	
}
