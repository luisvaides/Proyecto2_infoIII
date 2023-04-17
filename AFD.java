import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class AFD {
    private int numEstados;
    private Set<Integer> estadosFinales;
    private HashMap<Character, Integer> alfabeto;
    private int[][] transicion;

    public AFD(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            numEstados = Integer.parseInt(br.readLine());
            estadosFinales = new HashSet<>();
            for (String estado : br.readLine().split(",")) {
                estadosFinales.add(Integer.parseInt(estado));
            }
            String[] simbolos = br.readLine().split(",");
            alfabeto = new HashMap<>();
            for (int i = 0; i < simbolos.length; i++) {
                alfabeto.put(simbolos[i].charAt(0), i);
            }
            transicion = new int[simbolos.length][numEstados];
            for (int i = 0; i < simbolos.length; i++) {
                int[] fila = Arrays.stream(br.readLine().split(",")).mapToInt(Integer::parseInt).toArray();
                for (int j = 0; j < fila.length; j++) {
                    transicion[i][j] = fila[j];
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getTransition(int currentState, char symbol) {
        return transicion[alfabeto.get(symbol)][currentState];
    }

    public boolean evaluate(String string) {
        int currentState = 1;
        for (char symbol : string.toCharArray()) {
            currentState = getTransition(currentState, symbol);
        }
        return isFinal(currentState);
    }

    public boolean[] evaluateMany(String[] strings) {
        boolean[] resultados = new boolean[strings.length];
        for (int i = 0; i < strings.length; i++) {
            resultados[i] = evaluate(strings[i]);
        }
        return resultados;
    }

    public boolean isFinal(int currentState) {
        return estadosFinales.contains(currentState);
    }

    public void printMinimum() {
        finalState(1, new StringBuilder());
    }
    
    private void finalState(int currentState, StringBuilder currentString) {
        if (isFinal(currentState)) {
            System.out.println(currentString.toString());
            return;
        }
    
        for (char symbol : alfabeto.keySet()) {
            int nextState = getTransition(currentState, symbol);
            if (nextState != 0) {
                currentString.append(symbol);
                finalState(nextState, currentString);
                currentString.setLength(currentString.length() - 1);
            }
        }
    }
        
}
