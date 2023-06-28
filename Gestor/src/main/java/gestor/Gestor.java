/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package gestor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import org.json.simple.JSONObject;
import java.io.File;
import java.util.Scanner;
import java.util.HashMap;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Comparator;
import static java.util.Comparator.comparingInt;
import java.util.LinkedHashMap;
import java.util.Map;
import static java.util.Map.Entry.comparingByValue;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import static java.util.stream.Collectors.toMap;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

//import java.util.Timer;
/**
 *
 * @authors 28891 - Romilson Monteiro 28915 - Ruben Da Luz
 */
public class Gestor {

    private static HashMap<String, Utilizador> utilizadores = new HashMap<>();
    private static HashMap<Integer, Backup> backups = new HashMap<>();
    private static HashMap<Integer, Impressao> impressoes = new HashMap<>();

    private static int IdServico, IdPedido;

    public static void main(String[] args) {

        recuperarDadosSalvos();
        Utilizador utilizador;
        do {
            try {
                utilizador = Login();
                switch (utilizador.getTipo()) {
                    case ADIMINISTRADOR ->
                        Administrador();
                    case CONSUMIDOR ->
                        Consumidor();
                    default ->
                        System.out.println("Erro !!!");
                }
            } catch (Exception e) {
                System.out.println("Erro !!!");
            }
        } while (true);

    }

    private static void Administrador() {
        Scanner scanner = new Scanner(System.in);
        int opcao = 7;
        do {
            try {
                atenderEmBackground();
                opcao = menuAdministrador();
                switch (opcao) {
                    case 1 ->
                        CRUDutilizador();
                    case 2 ->
                        CRUDserviscos();
                    case 3 -> {
                        requisicaoServico();
                    }
                    case 4 -> {
                        System.out.println("----------- SERVICOS Disponiveis----------");
                        ordenarServicoTipo();
                    }
                    case 5 ->
                        estatistica();
                    case 6 -> {

                        System.out.println("/-------- Alteracao de estado -------------/\n");
                        try {
                            System.out.print("ID >> ");
                            int id = scanner.nextInt();
                            if (impressoes.containsKey(id)) {
                                System.out.println("Estado alterado!!");
                                System.out.println("Estado para que deseja passar:");
                                System.out.println("\t 1 - Disponivel");
                                System.out.println("\t 2 - Indisponivel");
                                System.out.println("\t 3 - Ocupado");
                                System.out.println("\t 4 - Erro");
                                int op = scanner.nextInt();
                                switch (op) {
                                    case 1 -> {
                                        impressoes.get(id).setEstados(Servico.Estados.DISPONIVEL);
                                        System.out.println("Estado alterado!!");
                                    }
                                    case 2 -> {
                                        impressoes.get(id).setEstados(Servico.Estados.INDISPONIVEL);
                                        System.out.println("Estado alterado!!");
                                    }
                                    case 3 -> {
                                        impressoes.get(id).setEstados(Servico.Estados.OCUPADO);
                                        System.out.println("Estado alterado!!");
                                    }
                                    case 4 -> {
                                        impressoes.get(id).setEstados(Servico.Estados.ERRO);
                                        System.out.println("Estado alterado!!");
                                    }
                                    default ->
                                        System.out.println("opcao invalida");
                                }

                            } else if (backups.containsKey(id)) {
                                System.out.println("Estado alterado!!");
                                System.out.println("Estado para que deseja passar:");
                                System.out.println("\t 1 - Disponivel");
                                System.out.println("\t 2 - Indisponivel");
                                System.out.println("\t 3 - Ocupado");
                                System.out.println("\t 4 - Erro");
                                int op = scanner.nextInt();
                                switch (op) {
                                    case 1 -> {
                                        backups.get(id).setEstados(Servico.Estados.DISPONIVEL);
                                        System.out.println("Estado alterado!!");
                                    }
                                    case 2 -> {
                                        backups.get(id).setEstados(Servico.Estados.INDISPONIVEL);
                                        System.out.println("Estado alterado!!");
                                    }
                                    case 3 -> {
                                        backups.get(id).setEstados(Servico.Estados.OCUPADO);
                                        System.out.println("Estado alterado!!");
                                    }
                                    case 4 -> {
                                        backups.get(id).setEstados(Servico.Estados.ERRO);
                                        System.out.println("Estado alterado!!");
                                    }
                                    default ->
                                        System.out.println("opcao invalida");
                                }
                            } else {
                                System.out.println("Servisco nao encontrado!!!");
                            }
                        } catch (Exception e) {
                            System.out.println("Erro !!!");
                        }
                        System.out.println("/------------------------------------------/\n");
                    }

                    case 7 -> {
                        System.out.println("/---------------------  Servicos a serem processados   -------------------/");
                        listarServicosEmUtilizacao();
                        System.out.println("/--------------------------------------------------------------------------/");
                    }
                    case 8 ->
                        System.out.println("../");
                    case 0 ->
                        Salvar_Sair();
                    default ->
                        System.out.println("Opcao indisponivel !!!");
                }

            } catch (Exception e) {
                System.out.println("Erro !!!");
            }
        } while (opcao != 8);
    }

    private static void Consumidor() {
        int opcao = 3;

        do {
            try {
                atenderEmBackground();
                opcao = menuConsumidor();
                switch (opcao) {
                    case 1 -> {
                        requisicaoServico();
                    }
                    case 2 -> {
                        System.out.println("----------- SERVICOS Disponiveis----------");
                        ordenarServicoTipo();
                    }
                    case 3 ->
                        System.out.println("../");
                    case 0 ->
                        Salvar_Sair();
                    default ->
                        System.out.println("Opcao invalida !!!");
                }
            } catch (Exception e) {
                System.out.println("Erro !!!");
            }

        } while (opcao != 3);
    }

    private static void CRUDutilizador() {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        String email;
        do {
            opcao = menuCRUDutilizador();
            switch (opcao) {
                case 1 -> {
                    System.out.println("/---------------   Registo de Utilizador  -------------/");

                    Utilizador user = ReceberDadosUtil();
                    if (user != null) {
                        utilizadores.put(user.getEmail(), user);
                    }

                    System.out.println("/------------------------------------------------------/");
                }
                case 2 -> {
                    System.out.println("/------------- Alteracao dos dados do utilizador --------------/");
                    System.out.print("Email do utilizador:\n\t»");
                    email = scanner.next();

                    if (utilizadores.get(email) != null) {
                        alterarDadosUser(email);
                        System.out.println("Dados do utilizador alterado com Sucesso");
                    } else {
                        System.out.println("Email invalido!!!");
                    }
                    System.out.println("/--------------------------------------------------------------/\n");
                }

                case 3 -> {

                    System.out.println("/-----------------  Remocao de Utilizador  -------------/\n");
                    System.out.print("Email:");
                    email = scanner.next();

                    if (utilizadores.get(email) != null) {
                        removerUser(email);
                        System.out.println("Utilizador removido com Sucesso");
                    } else {
                        System.out.println("Email invalido!!!");
                    }
                    System.out.println("/-------------------------------------------------------/\n");
                }

                case 4 -> {

                    for (String email1 : utilizadores.keySet()) {
                        Utilizador user = utilizadores.get(email1);
                        user.print();
                    }
                    System.out.println("/--------------------------------------------------------------/\n");
                }
                case 5 ->
                    System.out.println("../");
                case 0 ->
                    Salvar_Sair();
                default ->
                    System.out.println("Erro !!!");
            }
        } while (opcao != 5);

    }

    private static void CRUDserviscos() {
        int opcao, op;
        Scanner scanner = new Scanner(System.in);

        do {
            opcao = menuCRUDserviscos();

            switch (opcao) {
                case 1 -> {
                    System.out.println("/--------- Registro de Servico ----------/");
                    outerLoop:

                    do {
                        op = menuTipoServico();

                        switch (op) {
                            case 1 -> {
                                criarServico(Servico.Tipos.IMPRESSAO);
                                break outerLoop;
                            }
                            case 2 -> {
                                criarServico(Servico.Tipos.BACKUP);
                                break outerLoop;
                            }

                            case 0 ->
                                Salvar_Sair();
                            default ->
                                System.out.println("Tipo de Servico invalido !!!");
                        }
                    } while (op != 3);
                    System.out.println("/-----------------------------------------/\n");
                }
                case 2 -> {

                    System.out.println("/--------- Alteracao dos dados de um Servico ----------/");
                    if (impressoes.isEmpty() && backups.isEmpty()) {
                        System.out.println("Ainda nao foi insirido nenhum Servico");
                    } else {
                        alterarServico();
                    }
                    System.out.println("/-----------------------------------------------------/\n");
                }
                case 3 -> {
                    System.out.println("/-------- Remocao de Servico -------------/\n");
                    System.out.print("ID >> ");
                    int id = scanner.nextInt();
                    if (impressoes.containsKey(id)) {
                        System.out.println("Servico Removido!!");
                        impressoes.remove(id);
                    } else if (backups.containsKey(id)) {
                        backups.remove(id);
                        System.out.println("Servico Removido!!");
                    } else {
                        System.out.println("Servisco nao encontrado!!!");
                    }
                    System.out.println("/------------------------------------------/\n");
                    break;
                }
                case 5 -> {
                    System.out.println("/-------------- Procura de servico  ------------------/\n");
                    System.out.print("ID >>");
                    int id = scanner.nextInt();
                    Impressao imp = impressoes.get(id);
                    if (imp != null) {
                        imp.ptintImp();
                    } else {
                        System.out.println("Servico com id-[], nao foi encontrado!");
                    }
                    System.out.println("/-----------------------------------------------------/\n");
                    break;
                }
                case 4 -> {
                    System.out.println("Escolha asseguir como pretende ordenar");
                    listarServicos();
                    System.out.println("/--------------------------------------------------------------------------------------/");
                }
                case 6 ->
                    System.out.println("../");
                case 0 ->
                    Salvar_Sair();
                default ->
                    System.out.println("Opcao invalida !!!");
            }
        } while (opcao != 6);
    }

    //-----------------------------------------------------------------------------
    //       Funcoes relacionadas ao Utilizador
    //-----------------------------------------------------------------------------
    private static Utilizador Login() {
        Scanner scanner = new Scanner(System.in);
        String email;
        String password;
        if (utilizadores.isEmpty()) {
            Utilizador userA = new Utilizador("Administrador Romilson", "308706471", "romilson", "1234", Utilizador.Tipos.ADIMINISTRADOR, "Departamento Tecnico", "Chefe", 1224);
            Utilizador userC = new Utilizador("Consumidor Ruben", "0000", "ruben", "1234", Utilizador.Tipos.CONSUMIDOR, "Recursos Humanos", "Chefe", 1224);
            utilizadores.put(userA.getEmail(), userA);
            utilizadores.put(userC.getEmail(), userC);

        }

        int opcao;
        Utilizador utilizador;
        do {
            opcao = menuIncial();
            switch (opcao) {

                case 1 -> {
                    System.out.println("/--------------->>> Login <<<<---------------/");
                    System.out.print("[] Email    : ");
                    email = scanner.next();
                    Utilizador user = utilizadores.get(email);
                    if (user != null) {
                        System.out.print("[] Password : ");
                        password = scanner.next();
                        if (user.getPassword().equals(password)) {
                            System.out.println("Ben vindo, " + user.getNome() + "!");
                            utilizador = user;
                            System.out.println("/-------------------------------------------/\n");
                            return utilizador;
                        } else {
                            System.out.println("Password incoreto !!!");
                        }
                    } else {
                        System.out.println("Utilizador com o respectivo email nao encontrado !!!!!!");
                    }

                    System.out.println("/-------------------------------------------/");
                }
                case 0 -> {
                    Salvar_Sair();
                }
                default ->
                    System.out.println("Opção inválida!");
            }
        } while (true);

    }

    // Funcao para receber dados de um utilizador
    private static Utilizador ReceberDadosUtil() {
        Scanner scanner = new Scanner(System.in);
        Utilizador utilizador;
        Utilizador.Tipos tipo;
        String email, password, nif, nome, departamento, cargo;
        int opcao, numCC;

        System.out.print("Nome: ");
        nome = scanner.next();
        System.out.print("Numero de CC: ");
        numCC = scanner.nextInt();
        System.out.print("Nif: ");
        nif = scanner.next();
        System.out.println("Tipo de Utilizador: ");
        OUTER:
        while (true) {
            System.out.println("\t1 - Adiministrador ");
            System.out.println("\t2 - Consumidor");
            System.out.print(" »»  ");
            opcao = scanner.nextInt();
            switch (opcao) {
                case 1 -> {
                    tipo = Utilizador.Tipos.ADIMINISTRADOR;
                    break OUTER;
                }
                case 2 -> {
                    tipo = Utilizador.Tipos.CONSUMIDOR;
                    break OUTER;
                }
                default ->
                    System.out.println("Opcao invalida!!!");
            }
        }
        System.out.print("Cargo: ");
        cargo = scanner.next();
        System.out.print("Departamento:");
        departamento = scanner.next();
        System.out.println("[] Email:");

        while (true) {
            System.out.print(" »  ");
            email = scanner.next();
            if (utilizadores.containsKey(email)) {
                System.out.println("Esse email ja esta registrado! Incira um diferente");
                System.out.println("Deseja tentar um outro email?");
                System.out.println("\t s - sim");
                System.out.println("\t n - nao");
                System.out.print(" \t»  ");
                char op = scanner.next().charAt(0);
                if (op == 'n') {
                    return null;
                }
            } else {
                break;
            }
        }
        System.out.print("Password: ");
        password = scanner.next();
        utilizador = new Utilizador(nome, nif, email, password, tipo, departamento, cargo, numCC);
        return utilizador;
    }

    //funcao para remover o utilizador de um map
    public static void removerUser(String email) {
        utilizadores.remove(email);
    }

    //funcao para remover o utilizador de um map
    public static void alterarDadosUser(String email) {
        Utilizador user = ReceberDadosUtil();
        utilizadores.put(email, user);
    }

    //-----------------------------------------------------------------------------
    //       Funcoes para guardar e recupear estado de utilizacao da aplicacao
    //-----------------------------------------------------------------------------
    // Funcao que guarda o estado  da aplicacao num ficheiro binario
    public static void recuperarDadosSalvos() {
        try {
            File f1 = new File("utilizadores.bin");
            if (f1.exists()) {
                try ( FileInputStream fis1 = new FileInputStream("utilizadores.bin");  ObjectInputStream ois1 = new ObjectInputStream(fis1)) {
                    utilizadores = (HashMap<String, Utilizador>) ois1.readObject();
                }
            } else {
                utilizadores = new HashMap<>();
            }

            File f2 = new File("impressoes.bin");
            if (f2.exists()) {
                try ( FileInputStream fis2 = new FileInputStream("impressoes.bin");  ObjectInputStream ois2 = new ObjectInputStream(fis2)) {
                    impressoes = (HashMap<Integer, Impressao>) ois2.readObject();
                }
            } else {
                impressoes = new HashMap<>();
            }

            File f3 = new File("backups.bin");
            if (f3.exists()) {
                FileInputStream fis3 = new FileInputStream("backups.bin");
                try ( ObjectInputStream ois3 = new ObjectInputStream(fis3)) {
                    backups = (HashMap<Integer, Backup>) ois3.readObject();
                }
            } else {
                backups = new HashMap<>();
            }

            File f4 = new File("ids.bin");
            if (f4.exists()) {

                try ( FileInputStream fis3 = new FileInputStream("ids.bin");  DataInputStream dis = new DataInputStream(fis3)) {
                    IdServico = dis.readInt();
                    IdPedido = dis.readInt();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                IdServico = 1;
                IdPedido = 1;
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro na memória !!" + e);
        }
    }

    // Funcao para recuperar e repor o estado da ultima utilizacao da aplicacao que se encontra salvo num ficheiro binario
    public static void Salvar_Sair() {
        try {
            FileOutputStream fos = new FileOutputStream("utilizadores.bin", false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(utilizadores);
            oos.close();
            fos.close();

            FileOutputStream fos1 = new FileOutputStream("impressoes.bin", false);
            ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
            oos1.writeObject(impressoes);
            oos1.close();
            fos1.close();

            FileOutputStream fos2 = new FileOutputStream("backups.bin", false);
            ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
            oos2.writeObject(backups);
            oos2.close();
            fos2.close();

            FileOutputStream fos3 = new FileOutputStream("ids.bin", false);
            DataOutputStream dos3 = new DataOutputStream(fos3);
            dos3.writeInt(IdServico);
            dos3.writeInt(IdPedido);
            dos3.close();
            fos3.close();

        } catch (IOException e) {
            System.out.println("Erro na memória !!" + e);
        }
        // Fechar o progama
        System.out.println("Adeus !!");
        System.exit(0);
    }

    /**
     * Funcoes relacionadas ao servicos
     *
     * @param tipo
     */
    // Criar um servico e colocar numa map
    public static void criarServico(Servico.Tipos tipo) {
        Scanner scanner = new Scanner(System.in);
        String nome;
        String impressora;
        int capacidade;

        System.out.print("Nome: ");
        nome = scanner.next();

        switch (tipo) {
            case IMPRESSAO -> {
                System.out.print("Nome da impressora: ");
                impressora = scanner.next();
                impressoes.put(IdServico, new Impressao(nome, IdServico, Servico.Estados.DISPONIVEL, impressora));
                System.out.println("\n Servico inserido com sucesso!!!");
                IdServico++;
            }
            case BACKUP -> {
                System.out.print("Capacidade: ");
                capacidade = scanner.nextInt();
                backups.put(IdServico, new Backup(nome, IdServico, Servico.Estados.DISPONIVEL, capacidade));
                System.out.println("\n Servico inserido com sucesso !!!!");
                IdServico++;
            }
        }
    }

    private static void listarServicos() {
        int op = menuListar();
        switch (op) {
            case 1 -> {
                System.out.println("/-------------------- Lista de Servicos ordenados por Estdao -----------------------/");
                listServicesPorEstado();
            }
            case 2 -> {
                System.out.println("/-------------------- Lista de Servicos ordenados por nome -----------------------/");
                ordenarServicoNome();
            }
            case 3 -> {
                System.out.println("/-------------------- Lista de Servicos ordenados por tipo -----------------------/");
                ordenarServicoTipo();
            }
            case 4 -> {
                System.out.println("/-------------------- Lista de Servicos a Utilizacao ------------------------------/");
                listarServicosEmUtilizacao();
            }
            default -> System.out.println("Opcao invalida");
        }
    }

    //funcao para alterar dados de um utilizador que esta num map
    public static void alterarServico() {
        Scanner scanner = new Scanner(System.in);
        int op, id;
        outerLoo:
        do {
            op = menuTipoServico();
            switch (op) {
                case 1 -> {
                    if (!impressoes.isEmpty()) {
                        System.out.print("ID do Servico:\n\t»");
                        id = scanner.nextInt();

                        if (impressoes.containsKey(id)) {
                            alterarDadosServico(id, Servico.Tipos.IMPRESSAO);
                            System.out.println("Dados alterado com sucesso!");

                            break outerLoo;
                        } else {
                            System.out.println("Servico  nao existe!");
                        }
                    } else {
                        System.out.println("Nao existe nenhun servico desse tipo!");
                    }
                }
                case 2 -> {
                    if (!backups.isEmpty()) {
                        System.out.print("ID do Servico:\n\t»");
                        id = scanner.nextInt();
                        if (backups.containsKey(id)) {
                            alterarDadosServico(id, Servico.Tipos.BACKUP);
                            break outerLoo;

                        } else {
                            System.out.println("Servico  nao existe!");
                        }
                    } else {
                        System.out.println("Nao existe nenhun servico desse tipo!");
                    }
                }
                case 3 ->
                    System.out.println("../");
                case 0 ->
                    Salvar_Sair();
                default ->
                    System.out.println("Tipo de Servico invalido !!!");
            }
        } while (op != 3);
    }

    // Funcao para procurar servico
    public static void procurarServico() {
        Scanner scanner = new Scanner(System.in);
        int id;

        System.out.print("ID do Servico:\n\t»");
        id = scanner.nextInt();
        // procurar na lista de servicos de impressoes
        if (impressoes.containsKey(id)) {
            impressoes.get(id).ptintImp();
        } else if (backups.containsKey(id)) {
            backups.get(id).ptintCap();
        } else {
            System.out.println("Servisco nao encontrado!!!");
        }

    }

    // funcao responsavel pela requisicao dos servicos
    private static void requisicaoServico() {
        Scanner scanner = new Scanner(System.in);
        int idServico, opcao;

        opcao = menuRequisicaoServico();
        OUTER:
        switch (opcao) {
            case 1 -> {
                System.out.print("Id do serviço:");
                idServico = scanner.nextInt();
                if (impressoes.containsKey(idServico)) {
                    requisitarImpressao(idServico);
                    System.out.println("Servico adicionado a fila de pedidos");
                    IdPedido++;
                } else if (backups.containsKey(idServico)) {
                    requisitarBackup(idServico);
                    System.out.println("Servico adicionado a fila de pedidos");
                    IdPedido++;
                } else {
                    System.out.println("Servico inexistente!!!");
                }
            }
            case 2 -> {
                requisitarServicoNome();
            }
            case 3 -> {
                int op;
                do {
                    op = menuTipoServico();
                    switch (op) {
                        case 1 -> {
                            requisitarImpressaoDisponivel();
                            break OUTER;
                        }
                        case 2 -> {
                            requisitarBackupDisponivel();
                            break OUTER;

                        }
                        case 3 ->
                            System.out.println("../");
                        case 0 ->
                            Salvar_Sair();
                        default ->
                            System.out.println("Opcao invalida!!");
                    }
                } while (op != 3);
            }
            case 4 ->
                System.out.println("../");
            case 0 ->
                Salvar_Sair();
            default ->
                System.out.println("Opcao invalida!!");

        }
    }

    //funcao para alterar informacoes ou dados de um Servico
    public static void alterarDadosServico(int i, Servico.Tipos tipo) {
        Scanner scanner = new Scanner(System.in);
        String nome;
        String impressora;
        String capacidade;
        System.out.println("Incira os novos dados ou prima enter para manter");
        switch (tipo) {
            case IMPRESSAO -> {
                Impressao oldImpressao = impressoes.get(i);
                System.out.print("Nome do Servico[ " + oldImpressao.getNome() + " ]: ");
                nome = scanner.nextLine();
                if (!nome.equals("")) {
                    impressoes.get(i).setNome(nome);
                }
                System.out.print("Nome da impressora  [ " + oldImpressao.getImpressora() + " ]: ");
                impressora = scanner.nextLine();
                if (!impressora.equals("")) {
                    impressoes.get(i).setImpressora(impressora);
                }

            }
            case BACKUP -> {
                Backup oldBakcup = backups.get(i);
                System.out.print("Nome do Servico  [ " + oldBakcup.getNome() + " ]: ");
                nome = scanner.nextLine();
                if (!nome.equals("")) {
                    backups.get(i).setNome(nome);
                }
                System.out.print("Capacidade  [ " + oldBakcup.getCapacidade() + " ]: ");
                capacidade = scanner.nextLine();
                if (!capacidade.equals("")) {
                    backups.get(i).setCapacidade(Integer.parseInt(capacidade));
                }
            }
        }

    }

    private static int verificarNomeServico(String nome, Servico.Tipos tipo) {
        int n = 0;
        switch (tipo) {
            case IMPRESSAO -> {
                for (int idImp : impressoes.keySet()) {
                    Impressao impressao = impressoes.get(idImp);
                    if (impressao.getNome() == null ? nome == null : impressao.getNome().equals(nome)) {
                        n++;
                    }
                }
            }
            case BACKUP -> {
                for (int idBac : backups.keySet()) {
                    Backup backup = backups.get(idBac);
                    if (backup.getNome() == null ? nome == null : backup.getNome().equals(nome)) {
                        n++;
                    }
                }
            }
        }

        return n;
    }

    public static int procurarServicoNome(String nome, Servico.Tipos tipo) {
        int i = 0;

        switch (tipo) {
            case IMPRESSAO -> {
                for (int idImp : impressoes.keySet()) {
                    Impressao impressao = impressoes.get(idImp);
                    if (impressao.getNome() == null ? nome == null : impressao.getNome().equals(nome)) {
                        return impressao.getID();
                    }
                    i++;
                }
            }
            case BACKUP -> {
                for (int idBac : impressoes.keySet()) {
                    Backup backup = backups.get(idBac);
                    if (backup.getNome() == null ? nome == null : backup.getNome().equals(nome)) {
                        return backup.getID();
                    }
                }
                i++;
            }
        }

        return i;
    }

    // funcao para fazer a requisicao de um servico de empresao
    public static void requisitarImpressao(int idServico) {
        Scanner scanner = new Scanner(System.in);
        enum TipoPag {
            A5, A4, A3, A2, A1
        }
        enum Disposicao {
            HORIZONTAL, VERTICAL
        }
        enum Cor {
            COR, PRETO_BRANCO
        }
        String ficheiro;
        int numCopias;

        JSONObject descricao = new JSONObject();
        char op;

        System.out.println("Nome ou Localizacao do ficheiro: \n >>>");
        ficheiro = scanner.next();
        descricao.put("ficheiro", ficheiro);

        System.out.print("Numero de Copias: \n >>>");
        numCopias = scanner.nextInt();
        descricao.put("qtd", numCopias);

        System.out.println("Cor: ");
        System.out.print("\t c - cor \n\t p - preto e branco \n >>>");
        op = scanner.next().charAt(0);
        if (op == 'c') {
            descricao.put("cor", Cor.COR);
        } else {
            descricao.put("cor", Cor.PRETO_BRANCO);
        }
        System.out.println("Tipo de Pagina: ");
        System.out.println("\t 1- A1 \t 3- A3 \t 5- A5");
        System.out.print("\t 2- A2 \t 4- A4 \n  >>>");
        op = scanner.next().charAt(0);
        switch (op) {
            case 1 ->
                descricao.put("tipoPag", TipoPag.A1);
            case 2 ->
                descricao.put("tipoPag", TipoPag.A2);
            case 3 ->
                descricao.put("tipoPag", TipoPag.A3);
            case 5 ->
                descricao.put("tipoPag", TipoPag.A5);
            default ->
                descricao.put("tipoPag", TipoPag.A4);
        }

        System.out.println("Disposiçao: ");
        System.out.print("\t h - horizontal \n\t v - vertical\n  >>>");
        op = scanner.next().charAt(0);
        if (op == 'h') {
            descricao.put("disposicao", Disposicao.HORIZONTAL);
        } else {
            descricao.put("disposicao", Disposicao.VERTICAL);
        }

        Pedidos pedido = new Pedidos(IdPedido, descricao);
        impressoes.get(idServico).addPedido(pedido);
        impressoes.get(idServico).setNumUtilizacao();

    }

    //
    public static void requisitarBackup(int idServico) {
        Scanner scanner = new Scanner(System.in);
        String ficheiro;
        int espacoOcupar;
        JSONObject descricao = new JSONObject();

        System.out.print("Informe o nome do ficheiro: \n >>>");
        ficheiro = scanner.next();
        System.out.print("Dimensao do ficheiro(GB): \n >>>");
        espacoOcupar = scanner.nextInt();
        descricao.put("ficheiro", ficheiro);
        descricao.put("qtd", espacoOcupar);

        Pedidos pedido = new Pedidos(IdPedido, descricao);
        backups.get(idServico).addPedido(pedido);
        backups.get(idServico).setNumUtilizacao();
    }

    public static void requisitarServicoNome() {
        Scanner scanner = new Scanner(System.in);
        int n, op;

        System.out.print("Introduza o nome do serviço que deseja: \n >>>");
        String nomeServico = scanner.next();
        op = menuTipoServico();
        switch (op) {
            case 1 -> {
                n = verificarNomeServico(nomeServico, Servico.Tipos.IMPRESSAO);
                if (n == 1) {
                    for (int id : impressoes.keySet()) {
                        Impressao impressao = impressoes.get(id);
                        if (impressao.getNome() == null ? nomeServico == null : impressao.getNome().equals(nomeServico)) {
                            requisitarImpressao(id);
                            System.out.println("Servico adicionado a fila de pedidos");
                            IdPedido++;
                        }
                    }
                } else if (n > 1) {
                    System.out.println("Existe mais de um servico com esse nome. Escolha um servico:");
                    for (int id : impressoes.keySet()) {
                        Impressao impressao = impressoes.get(id);
                        if (impressao.getNome() == null ? nomeServico == null : impressao.getNome().equals(nomeServico)) {
                            impressao.ptint();
                        }
                    }
                    System.out.print("id --->>");
                    int id = scanner.nextInt();
                    if (impressoes.containsKey(id)) {
                        requisitarImpressao(id);
                        System.out.println("Servico adicionado a fila de pedidos");
                        IdPedido++;
                    } else {
                        System.out.println("Servico inexistente!!!");
                    }

                } else {
                    System.out.println("Servico inexistente!!!");
                }
            }

            case 2 -> {
                n = verificarNomeServico(nomeServico, Servico.Tipos.BACKUP);
                if (n == 1) {
                    for (int id : backups.keySet()) {
                        Backup backup = backups.get(id);
                        if (backup.getNome() == null ? nomeServico == null : backup.getNome().equals(nomeServico)) {
                            requisitarBackup(id);
                            System.out.println("Servico adicionado a fila de pedidos");
                            IdPedido++;
                        }
                    }
                } else if (n > 1) {
                    System.out.println("Existe mais de um servico com esse nome. Escolha um servico:");
                    for (int id : backups.keySet()) {
                        Backup backup = backups.get(id);
                        if (backup.getNome() == null ? nomeServico == null : backup.getNome().equals(nomeServico)) {
                            backup.ptint();
                        }
                    }
                    System.out.print("id --->>");
                    int id = scanner.nextInt();
                    if (backups.containsKey(id)) {
                        requisitarBackup(id);
                        System.out.println("Servico adicionado a fila de pedidos");
                        IdPedido++;
                    } else {
                        System.out.println("Servico inexistente!!!");
                    }

                } else {
                    System.out.println("Servico inexistente!!!");
                }

            }

        }
    }

    private static int probabilidade() {
        Random random = new Random();
        int chance = random.nextInt(100);
        return chance;
    }

    // Apresentar todos as estatistica
    private static void estatistica() {
        Scanner scanner = new Scanner(System.in);

        int T_bac = 0, T_imp = 0; // t_bac total de backups efetuadas , T_imp total de impressoes efetuadas/
        float total = 1; //total - no. de utilizacao de todos servico efetuadas
        float percentagem;
        Servico MaiorUtilizacao_Imp = null;
        Servico MenorUtilizacao_Bac = null;
        Servico MaiorUtilizacao_Bac = null;
        Servico MenorUtilizacao_Imp = null;

        if (!backups.isEmpty()) {
            for (int idBac : backups.keySet()) {
                T_bac = T_bac + backups.get(idBac).getNumUtilizacao();
            }
        }
        if (!impressoes.isEmpty()) {
            for (int idImp : impressoes.keySet()) {
                T_imp = T_imp + impressoes.get(idImp).getNumUtilizacao();
            }
        }

        total = T_bac + T_imp;

        int op;
        boolean status = false;
        do {
            op = menuEstatistica();
            switch (op) {
                case 1 -> {
                    if (!backups.isEmpty()) {
                        for (int id : backups.keySet()) {
                            Backup bac = backups.get(id);
                            System.out.println("Backup # " + bac.getNome() + "[" + bac.getID() + "]");
                            System.out.println("\tQuantidade:" + bac.getNumUtilizacao());
                            percentagem = bac.getNumUtilizacao() * 100 / total;
                            System.out.println("\tPercentagem:" + percentagem + "%");
                            status = true;
                        }
                    }
                    if (!impressoes.isEmpty()) {
                        for (int id : impressoes.keySet()) {
                            Impressao imp = impressoes.get(id);
                            System.out.println("Impressao  # " + imp.getNome() + "[" + imp.getID() + "]");
                            System.out.println("\tQuantidade:" + imp.getNumUtilizacao());
                            percentagem = imp.getNumUtilizacao() * 100 / total;
                            System.out.println("\tPercentagem:" + percentagem + "%");
                            status = true;
                        }
                    }
                    if (status == true) {
                        System.out.print("\n Deseja exportar a estatistica?(s/n)\n>>");
                        char op1 = scanner.next().charAt(0);
                        if (op1 == 's') {
                            try {
                                // Creating the CSV file with the name "estatisticas.csv"
                                FileWriter writer = new FileWriter("estatisticas3.csv");

                                // Creating the header of the file
                                String[] header = {"Tipo de Servico", "Servico", "Quantidade Utilizacoes", "Percentagem"};
                                // Creating the rows of the file with the statistics
                                try ( CSVPrinter csvWriter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(header).withDelimiter('\t'))) {
                                    for (int id : backups.keySet()) {
                                        Backup bac = backups.get(id);
                                        percentagem = bac.getNumUtilizacao() * 100 / total;
                                        csvWriter.printRecord("Backup", bac.getNome(), bac.getNumUtilizacao(), percentagem + "%");

                                    }

                                    for (int id : impressoes.keySet()) {
                                        Impressao imp = impressoes.get(id);
                                        percentagem = imp.getNumUtilizacao() * 100 / total;
                                        csvWriter.printRecord("Impressao", imp.getNome(), imp.getNumUtilizacao(), percentagem + "%");
                                    }
                                    // Closing the file
                                    writer.close();
                                }
                                System.out.println("Estatistica exportada para estatisticas1.csv");
                            } catch (IOException e) {
                                System.out.println("Erro ao exportar estatistica:" + e);
                            }
                        } else {
                            System.out.println("Estatísticas nao exportada !");
                        }
                    }

                }

                case 2 -> {
                    status = false;
                    if (!backups.isEmpty()) {
                        for (int id : backups.keySet()) {
                            if (MaiorUtilizacao_Bac == null || MaiorUtilizacao_Bac.getNumUtilizacao() < backups.get(id).getNumUtilizacao()) {
                                MaiorUtilizacao_Bac = backups.get(id);
                            }
                            if (MenorUtilizacao_Bac == null || MenorUtilizacao_Bac.getNumUtilizacao() > backups.get(id).getNumUtilizacao()) {
                                MenorUtilizacao_Bac = backups.get(id);
                            }
                        }
                        status = true;
                    }
                    if (!impressoes.isEmpty()) {
                        for (int id : impressoes.keySet()) {
                            if (MaiorUtilizacao_Imp == null || MaiorUtilizacao_Imp.getNumUtilizacao() < impressoes.get(id).getNumUtilizacao()) {
                                MaiorUtilizacao_Imp = impressoes.get(id);
                            }
                            if (MenorUtilizacao_Imp == null || MenorUtilizacao_Imp.getNumUtilizacao() > impressoes.get(id).getNumUtilizacao()) {
                                MenorUtilizacao_Imp = impressoes.get(id);
                            }
                        }
                        status = true;
                    }
                    if (status == true) {
                        System.out.println("Servicos com maior Utilizacao:");
                        if (MaiorUtilizacao_Bac != null) {
                            MaiorUtilizacao_Bac.ptint();
                            System.out.printf("Qtd: %d", MaiorUtilizacao_Bac.getNumUtilizacao());
                        }
                        if (MaiorUtilizacao_Imp != null) {
                            MaiorUtilizacao_Imp.ptint();
                            System.out.printf("Qtd: %d", MaiorUtilizacao_Imp.getNumUtilizacao());
                        }

                        System.out.println("Servicos com menor Utilizacao:");
                        if (MenorUtilizacao_Bac != null) {
                            MenorUtilizacao_Bac.ptint();
                            System.out.printf("Qtd: %d", MenorUtilizacao_Bac.getNumUtilizacao());
                        }
                        if (MenorUtilizacao_Imp != null) {
                            MenorUtilizacao_Imp.ptint();
                            System.out.printf("Qtd: %d", MenorUtilizacao_Imp.getNumUtilizacao());
                        }

                        System.out.print("\n Deseja exportar a estatistica?(s/n)\n>>");
                        char op1 = scanner.next().charAt(0);
                        if (op1 == 's') {
                            try {
                                // Criação do arquivo CSV com o nome "estatisticas.csv"
                                FileWriter writer = new FileWriter("estatisticas2.csv");

                                // Criação do cabeçalho do arquivo
                                String[] cabeçalho = {"Tipo de Servico", "Nome Servico", "Quantidade Utilizacoes", "Percentagem", "Servico com Maior Utilizacao", "Servico com Menor Utilizao"};

                                // Criação das linhas do arquivo com as estatísticas
                                try ( CSVPrinter csvWriter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(cabeçalho).withDelimiter('\t'))) {
                                    // Criação das linhas do arquivo com as estatísticas

                                    if (MenorUtilizacao_Imp != null && MaiorUtilizacao_Imp != null) {
                                        percentagem = T_bac * 100 / total;
                                        String[] linha1 = {"Backup", String.valueOf(T_bac), String.valueOf(percentagem) + "%", MaiorUtilizacao_Imp.getNome(), MenorUtilizacao_Imp.getNome()};
                                        csvWriter.printRecord((Object) linha1);  // Adicionando as linhas ao arquivo
                                    }

                                    if (MenorUtilizacao_Bac != null && MaiorUtilizacao_Bac != null) {
                                        percentagem = T_imp * 100 / total;
                                        String[] linha2 = {"Impressao", String.valueOf(T_imp), String.valueOf(percentagem) + "%", MaiorUtilizacao_Bac.getNome(), MenorUtilizacao_Bac.getNome()};
                                        csvWriter.printRecord((Object) linha2);   // Adicionando as linhas ao arquivo
                                    }

                                    // Fechando o arquivo
                                    writer.close();
                                }
                                System.out.println("Estatísticas exportadas para o arquivo estatisticas.csv");
                            } catch (IOException e) {
                                System.out.println("Erro ao exportar estatistica:" + e);
                            }
                        } else {
                            System.out.println("Estatísticas nao exportada !");
                        }
                    }

                }
                case 3 -> {
                    System.out.println("Impressao:");
                    System.out.println("\tQuantidade:" + T_imp);
                    System.out.println("\tPercentagem:" + T_imp * 100 / total + "%");

                    System.out.println("Backup:");
                    System.out.println("\tQuantidade:" + T_bac);
                    System.out.println("\tPercentagem:" + T_bac * 100 / total + "%");

                    System.out.print("\n Deseja exportar a estatistica?(s/n)\n>>");
                    char op1 = scanner.next().charAt(0);
                    if (op1 == 's') {
                        try {
                            // Creating the CSV file with the name "estatisticas.csv"
                            FileWriter writer = new FileWriter("estatisticas1.csv");

                            // Creating the header of the file
                            String[] header = {"Tipo de Servico", "Quantidade Utilizacoes", "Percentagem"};
                            // Creating the rows of the file with the statistics
                            try ( CSVPrinter csvWriter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(header).withDelimiter('\t'))) {
                                csvWriter.printRecord("Backup", T_bac, T_bac * 100 / total + "%");
                                csvWriter.printRecord("Impressao", T_imp, T_imp * 100 / total + "%");

                                // Closing the file
                                writer.close();
                            }
                            System.out.println("Estatistica exportada para estatisticas1.csv");
                        } catch (IOException e) {
                            System.out.println("Erro ao exportar estatistica:" + e);
                        }
                    } else {
                        System.out.println("Estatísticas nao exportada !");
                    }
                }
                case 4 -> {
                }
                case 0 ->
                    Salvar_Sair();
            }

        } while (op != 4);

    }

    /*
        * Esta é uma função que pausa uma ação após um determinado tempo passado como argumento. 
        * Utiliza a classe CountDownLatch para contar até o ponto de tempo e, em seguida, realizar a ação.
        * 
        * @param delayInSeconds tempo em segundos que a função deve esperar
     */
    public static void Timer(long delayInSeconds) {
        CountDownLatch latch = new CountDownLatch(1);

        // Inicia uma nova thread para contar até o latch
        new Thread(() -> {
            try {
                Thread.sleep(delayInSeconds * 1000); //convertendo segundos para milisegundos
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // Aguarda o latch ser contado
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void atenderPedido(Servico servico) {
        int tempo;
        JSONObject descricao = new JSONObject();
        System.out.println("1");
        if (servico.getPrimereiroPedido() != null) {
            while (!servico.getPedidos().isEmpty()) {
                descricao = servico.getPrimereiroPedido().getDescricao();
                tempo = (int) descricao.get("qtd");
                int chance = probabilidade();
                servico.setEstados(Servico.Estados.OCUPADO);
                Timer(tempo);

                //            Terminar atendimentos
                if (chance <= 75) {
                    servico.setEstados(Servico.Estados.DISPONIVEL);
                } else {
                    servico.setEstados(Servico.Estados.ERRO);
                }
                servico.apagarPedido();
            }

        }
    }

    private static void ordenarServicoTipo() {
        System.out.println("Servicos De Impressao :");

        for (int idImp : impressoes.keySet()) {
            Impressao imp = impressoes.get(idImp);
            imp.ptintImp();
        }

        System.out.println("Servicos De Backup :");
        for (int idBac : backups.keySet()) {
            Backup ba = backups.get(idBac);
            ba.ptintCap();
        }
    }

    private static void ordenarServicoNome() {
        Map<Integer, Servico> sortedServices = new LinkedHashMap<>();

        // Add all backups to the sorted map
        for (Map.Entry<Integer, Backup> entry : backups.entrySet()) {
            sortedServices.put(entry.getKey(), entry.getValue());
        }

        // Add all impressoes to the sorted map
        for (Map.Entry<Integer, Impressao> entry : impressoes.entrySet()) {
            sortedServices.put(entry.getKey(), entry.getValue());
        }

        // Sort the map by service name
        sortedServices = sortedServices.entrySet().stream().sorted(comparingByValue(Comparator.comparing(Servico::getNome))).collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        // Print the services
        for (Map.Entry<Integer, Servico> entry : sortedServices.entrySet()) {
            if (entry.getValue() instanceof Backup) {
                ((Backup) entry.getValue()).ptintCap();
            } else {
                ((Impressao) entry.getValue()).ptintImp();
            }
        }
    }

    public static void listServicesPorEstado() {
        Map<Integer, Servico> sortedServices = new LinkedHashMap<>();

        // Adiciona todos os backups para o mapa ordenado
        for (Map.Entry<Integer, Backup> entry : backups.entrySet()) {
            sortedServices.put(entry.getKey(), entry.getValue());
        }

        // Adiciona todas as impressões para o mapa ordenado
        for (Map.Entry<Integer, Impressao> entry : impressoes.entrySet()) {
            sortedServices.put(entry.getKey(), entry.getValue());
        }

        // Ordena o mapa pelo estado do serviço
        sortedServices = sortedServices.entrySet().stream()
                .sorted(comparingByValue(Comparator.comparing(Servico::getEstado)))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        // Imprime os serviços
        for (Map.Entry<Integer, Servico> entry : sortedServices.entrySet()) {
            if (entry.getValue() instanceof Backup) {
                ((Backup) entry.getValue()).ptintCap();
            } else {
                ((Impressao) entry.getValue()).ptintImp();
            }
        }
    }

    public static void listarServicosEmUtilizacao() {
        try {
            if (!impressoes.isEmpty()) {
                for (int id : impressoes.keySet()) {
                    Impressao imp = impressoes.get(id);
                    if (!imp.getPedidos().isEmpty()) {
                        for (Pedidos pedido : imp.getPedidos()) {
                            if (pedido != null) {
                                pedido.print();
                            }
                        }
                    }
                }
            }
            if (!backups.isEmpty()) {
                for (int id : backups.keySet()) {
                    Backup bac = backups.get(id);
                    if (!bac.getPedidos().isEmpty()) {
                        for (Pedidos pedido : bac.getPedidos()) {
                            if (pedido != null) {
                                pedido.print();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erro !!!" + e);
        }
    }

    private static void atenderEmBackground() {
        BackgroundTask.runInBackground(() -> {
            if (!impressoes.isEmpty()) {
                for (int id : impressoes.keySet()) {
                    Impressao imp = impressoes.get(id);
                    if (!imp.getPedidos().isEmpty()) {
                        BackgroundTask.runInBackground(() -> {
                            atenderPedido(imp);
                        });
                    }
                }
            }
            if (!backups.isEmpty()) {
                for (int id : backups.keySet()) {
                    Backup bac = backups.get(id);
                    if (!bac.getPedidos().isEmpty()) {
                        BackgroundTask.runInBackground(() -> {
                            atenderPedido(bac);
                        });
                    }
                }
            }
        });
    }

    private static void requisitarImpressaoDisponivel() {
        Map<Integer, Impressao> sortedImpressoes = impressoes.entrySet().stream().sorted(comparingByValue(Comparator.comparing(Impressao::getEstado, comparingInt(e -> e.ordinal())).thenComparing(Impressao::getPedidos, comparingInt(Queue::size)))).collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        for (Map.Entry<Integer, Impressao> entry : sortedImpressoes.entrySet()) {
            Impressao impressao = entry.getValue();
            if (impressao.getEstado() == Servico.Estados.DISPONIVEL) {
                requisitarImpressao(entry.getKey());
                System.out.println("Servico de impressão adicionado a fila de pedidos");
                IdPedido++;
                return;
            }
        }

        for (Map.Entry<Integer, Impressao> entry : sortedImpressoes.entrySet()) {
            Impressao impressao = entry.getValue();
            if (impressao.getEstado() != Servico.Estados.ERRO) {
                requisitarImpressao(entry.getKey());
                System.out.println("Servico de impressão adicionado a fila de pedidos");
                IdPedido++;
                return;
            }
        }
        System.out.println("Não existe serviços de impressão disponíveis no momento.");
    }

    private static void requisitarBackupDisponivel() {
        Map<Integer, Backup> sortedBackups = backups.entrySet().stream().sorted(comparingByValue(Comparator.comparing(Backup::getEstado, comparingInt(e -> e.ordinal())).thenComparing(Backup::getPedidos, comparingInt(Queue::size)))).collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        for (Map.Entry<Integer, Backup> entry : sortedBackups.entrySet()) {
            Backup backup = entry.getValue();
            if (backup.getEstado() == Servico.Estados.DISPONIVEL) {
                requisitarBackup(entry.getKey());
                System.out.println("Servico de backup adicionado a fila de pedidos");
                IdPedido++;
                return;
            }
        }
        for (Map.Entry<Integer, Backup> entry : sortedBackups.entrySet()) {
            Backup backup = entry.getValue();
            if (backup.getEstado() != Servico.Estados.ERRO) {
                requisitarBackup(entry.getKey());
                System.out.println("Servico de backup adicionado a fila de pedidos");
                IdPedido++;
                return;
            }

        }
        System.out.println("Não existe serviços de Backup disponíveis no momento.");
    }

    //*****************************************************************************
    //                  Funcoes Menus
    //*****************************************************************************
    private static int menuIncial() {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        System.out.println("+============================+");
        System.out.println("|        Menu Inicial        |");
        System.out.println("+============================+");
        System.out.println("| 1 -  Login                 |");
        System.out.println("| 0 -  Fechar o progama      |");
        System.out.println("+----------------------------+");
        System.out.print("Opção? >> ");
        opcao = scanner.nextInt();
        return opcao;
    }

    private static int menuAdministrador() {
        Scanner scanner = new Scanner(System.in);

        int opcao;
        System.out.println(".... Prima enter para continuar");
        scanner.nextLine();
        System.out.println("+============================================+");
        System.out.println("|            > > > Menu < < <                |");
        System.out.println("+============================================+");
        System.out.println("| 1  -  Operacoes CRUD sobre Utilizador      |");
        System.out.println("| 2  -  Operacoes CRUD sobre Servicos        |");
        System.out.println("| 3  -  Requisitar serviços                  |");
        System.out.println("| 4  -  Consultar Serviços                   |");
        System.out.println("| 5  -  Ver as estatisticas dos servicos     |");
        System.out.println("| 6  -  Mudar o Estado de um Servico         |");
        System.out.println("| 7  -  Lista de pedidos em processamento    |");
        System.out.println("| 8  -  Logout                               |");
        System.out.println("| 0  -  Fechar o progama                     |");
        System.out.println("+--------------------------------------------+");
        System.out.print("Opção? >> ");
        opcao = scanner.nextInt();
        return opcao;
    }

    private static int menuConsumidor() {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        System.out.println(".... Prima enter para continuar");
        scanner.nextLine();
        System.out.println("+==============================+");
        System.out.println("|      > > > Menu < <          |");
        System.out.println("+==============================+");
        System.out.println("| 1 -  Requisitar serviços     |");
        System.out.println("| 2 -  Consultar Serviços      |");
        System.out.println("| 3 -  Logout                  |");
        System.out.println("| 0 -  Fechar o progama        |");
        System.out.println("+------------------------------+");
        System.out.print("Opção? >> ");
        opcao = scanner.nextInt();
        return opcao;
    }

    private static int menuCRUDserviscos() {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        System.out.println("+===========================+");
        System.out.println("|      > > > Menu < <       |");
        System.out.println("+===========================+");
        System.out.println("| 1 -  Insirir Servicos     |");
        System.out.println("| 2 -  Alterar Servicos     |");
        System.out.println("| 3 -  Remover Servicos     |");
        System.out.println("| 4 -  Listar Servicos      |");
        System.out.println("| 5 -  Pesquisar Servicos   |");
        System.out.println("| 6 -  Voltar               |");
        System.out.println("| 0 -  Fechar o progama     |");
        System.out.println("+---------------------------+");
        System.out.print("Opção? >> ");
        opcao = scanner.nextInt();
        return opcao;
    }

    private static int menuCRUDutilizador() {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        System.out.println("+==================================+");
        System.out.println("|         > > > Menu < <           |");
        System.out.println("+==================================+");
        System.out.println("| 1 -  Insirir Utilizador          |");
        System.out.println("| 2 -  Alterar dados do Utilizador |");
        System.out.println("| 3 -  Remover Utilizador          |");
        System.out.println("| 4 -  Listar Utilizador           |");
        System.out.println("| 5 -  Voltar                      |");
        System.out.println("| 0 -  Fechar o progama            |");
        System.out.println("+----------------------------------+");
        System.out.print("Opção? >> ");
        opcao = scanner.nextInt();
        return opcao;
    }

    private static int menuRequisicaoServico() {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        System.out.println("+===================================+");
        System.out.println("|    > > Requisitar servicos < <    |");
        System.out.println("+===================================+");
        System.out.println("| 1  -  Por codigo;                 |");
        System.out.println("| 2  -  Por nome                    |");
        System.out.println("| 3  -  Mais disponivel de um tipo  |");
        System.out.println("| 4  -  Voltar                      |");
        System.out.println("+-----------------------------------+");
        System.out.print("Opção? >>");
        opcao = scanner.nextInt();
        return opcao;
    }

    private static int menuListar() {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        System.out.println("+===================================+");
        System.out.println("| > > Listar e Ordenar Serviços < < |");
        System.out.println("+===================================+");
        System.out.println("| 1  -  Por Estado                  |");
        System.out.println("| 2  -  Por nome                    |");
        System.out.println("| 3  -  Por tipo                    |");
        System.out.println("| 3  -  A utilizacao                |");
        System.out.println("| 5  -  Voltar                      |");
        System.out.println("+-----------------------------------+");
        System.out.print("Opção? >>");
        opcao = scanner.nextInt();
        return opcao;
    }

    private static int menuTipoServico() {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        System.out.println("+===================================+");
        System.out.println("|    Selecione o Tipo de serviço    |");
        System.out.println("+-----------------------------------+");
        System.out.println("| 1 -  Impressao                    |");
        System.out.println("| 2 -  Backup                       |");
        System.out.println("| 3 -  Voltar                       |");
        System.out.println("| 0 -  Fechar o progama             |");
        System.out.println("+-----------------------------------+");
        System.out.print(">> ");
        opcao = scanner.nextInt();
        return opcao;
    }

    private static int menuEstatistica() {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        System.out.println("+============================================+");
        System.out.println("|           Estatisticas Disponiveis         |");
        System.out.println("+============================================+");
        System.out.println("| 1 -  Utilização de cada serviço            |");
        System.out.println("| 2 -  Serviços com maior e menor utilização;|");
        System.out.println("| 3 -  Utilização De cada tipo de Servico;   | ");
        System.out.println("| 4 -  Voltar                                |");
        System.out.println("| 0 -  Fechar o progama                      |");
        System.out.println("+--------------------------------------------+");
        System.out.print(">> ");
        opcao = scanner.nextInt();
        return opcao;
    }
}
