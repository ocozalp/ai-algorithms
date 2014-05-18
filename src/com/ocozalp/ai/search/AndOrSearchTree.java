package com.ocozalp.ai.search;

import com.ocozalp.ai.common.Action;
import com.ocozalp.ai.common.Problem;
import com.ocozalp.ai.common.State;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by ocozalp on 5/18/14.
 */
public class AndOrSearchTree {

    private Problem problem;

    public AndOrSearchTree(Problem problem) {
        this.problem = problem;
    }

    public ArrayList<Action> search(State startState, State goalState) {
        ArrayList<Action> resultActions = new ArrayList<Action>();
        Stack<Action> actionStack = new Stack<Action>();

        boolean result = orSearch(startState, goalState, actionStack, new Stack<State>());

        if(!result) return null;

        while(!actionStack.empty()) {
            resultActions.add(0, actionStack.pop());
        }

        return resultActions;
    }

    private boolean orSearch(State currentState, State goalState,
                          Stack<Action> actionStack, Stack<State> stateStack) {
        if(currentState.equals(goalState)) return true;
        if(stateStack.contains(currentState)) return false;

        stateStack.push(currentState);
        ArrayList<Action> actions = problem.getActionsOfState(currentState);

        boolean result = false;

        for(Action action : actions) {
            ArrayList<State> results = problem.getResultsOfAction(currentState, action);

            actionStack.push(action);
            result |= andSearch(results, goalState, actionStack, stateStack);
            if(result) break;

            actionStack.pop();
        }

        stateStack.pop();
        return result;
    }

    private boolean andSearch(ArrayList<State> resultStates, State goalState,
                              Stack<Action> actionStack, Stack<State> stateStack) {

        boolean result = true;
        for(State nextState : resultStates) {
            result &= orSearch(nextState, goalState, actionStack, stateStack);
            if(!result) break;
        }
        return result;
    }
}
