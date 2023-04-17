import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class AFD {
    private int estados;
    private Set<Integer> estados_finales;
    private HashMap<Character, Integer> evaluaralf;
    private int[][] transicion;

    public AFD(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            estados = Integer.parseInt(br.readLine());
            estados_finales = new HashSet<>();
            Arrays.stream(br.readLine().split(",")).mapToInt(Integer::parseInt).forEach(estados_finales::add);
            
            String[] symbole = br.readLine().split(",");
            evaluaralf = new HashMap<>();
            for (int i = 0; i < symbole.length; i++) {
                evaluaralf.put(symbole [i].charAt(0), i);
            }
            
            transicion = new int[symbole.length][estados];
            for (int i = 0; i < symbole.length; i++) {
                transicion[i] = Arrays.stream(br.readLine().split(",")).mapToInt(Integer::parseInt).toArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getTransition(int currentState, char symbol) {
        int symbol2 = evaluaralf.get(symbol);
        return transicion[symbol2][currentState];    }

    public boolean evaluate(String string) {
        int currentState = 1;
        for (char symbol : string.toCharArray()) {
            currentState = procesarsymbol(currentState, symbol);
        }
        return isFinal(currentState);
    }
    
    private int procesarsymbol(int currentState, char symbol) {
        return getTransition(currentState, symbol);
    }

    public boolean[] evaluateMany(String[] strings) {
        boolean[] resultado = new boolean[strings.length];
        for (int i = 0; i < strings.length; i++) {
            resultado[i] = evaluate(strings[i]);
        }
        return resultado;
    }

    public boolean isFinal(int currentState) {
        return estados_finales.contains(currentState);
    }

    public void printMinimum() {
        finalState(1, new StringBuilder());
    }
    
    private void finalState(int currentState, StringBuilder currentString) {
        if (isFinal(currentState)) {
            System.out.println(currentString.toString());
            return;
        }
    
        for (char symbol : evaluaralf.keySet()) {
            int nextState = getTransition(currentState, symbol);
            if (nextState != 0) {
                currentString.append(symbol);
                finalState(nextState, currentString);
                currentString.setLength(currentString.length() - 1);
            }
        }
    }
        
}
