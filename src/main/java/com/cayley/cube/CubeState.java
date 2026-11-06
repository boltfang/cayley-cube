package com.cayley.cube;

import java.util.Arrays;
/**
 * Represents the state of a Rubik's cube using separate arrays for
 * corner and edge representations and orientations.
 */
public final class CubeState {
    private static final int CORNERS = 8;
    private static final int EDGES = 12;

    // There are 8 corner pieces that can be arranged in 8! ways and arranged in 3 orientations
    private final byte[] cornerPerm = new byte[CORNERS];
    private final byte[] cornerOri = new byte[CORNERS];

    // There are 12 edge pieces that can be arranged in 12! ways and arranged in 2 orientations
    private final byte[] edgePerm = new byte[EDGES];
    private final byte[] edgeOri = new byte[EDGES];

    /**
     * Creates a fully solved cube. This is the solved state.
     */
    public CubeState() {
        for (byte i = 0; i < CORNERS; i++) cornerPerm[i] = i;
        for (byte i = 0; i < EDGES; i++) edgePerm[i] = i;
    }

    /**
     * Creates a state from raw data.
     * Note: This only checks the length of the state to ensure that it is a valid cube state,
     * it does not check to determine whether this cube state is legally possible or not.
     */
    public CubeState(byte[] cornerPerm, byte[] cornerOri, byte[] edgePerm, byte[] edgeOri) {
        if (cornerPerm.length != CORNERS || cornerOri.length != CORNERS 
                || edgePerm.length != EDGES || edgeOri.length != EDGES) {
            throw new IllegalArgumentException("Array lengths do not create a valid cube state.");
        }
        System.arraycopy(cornerPerm, 0, this.cornerPerm, 0, CORNERS);
        System.arraycopy(cornerOri, 0, this.cornerOri, 0, CORNERS);
        System.arraycopy(edgePerm, 0, this.edgePerm, 0, EDGES);
        System.arraycopy(edgeOri, 0, this.edgeOri, 0, EDGES);
    }

    /**
     * Copy another cube state, while being fully independent.
     */
    public CubeState(CubeState other) {
        System.arraycopy(other.cornerPerm, 0, this.cornerPerm, 0, CORNERS);
        System.arraycopy(other.cornerOri, 0, this.cornerOri, 0, CORNERS);
        System.arraycopy(other.edgePerm, 0, this.edgePerm, 0, EDGES);
        System.arraycopy(other.edgeOri, 0, this.edgeOri, 0, EDGES);
    }

    /**
     * Determine what the new state of the cube will be after applying a move. Neither state is modified.
     * @param other The cube state representation of the effects of applying a move from default SOLVED state.
     * @return The cube state after the move is applied on the current cube state.
     */
    public CubeState multiply(CubeState other) {
        CubeState result = new CubeState();

        for (int i = 0; i < CORNERS; i++) {
            byte src = other.cornerPerm[i];
            result.cornerPerm[i] = this.cornerPerm[src];
            result.cornerOri[i] = (byte) ((this.cornerOri[src] + other.cornerOri[i]) % 3);
        }

        for (int i = 0; i < EDGES; i++) {
            byte src = other.edgePerm[i];
            result.edgePerm[i] = this.edgePerm[src];
            result.edgeOri[i] = (byte) (this.edgeOri[src] ^ other.edgeOri[i]);
        }
        return result;
    }

    /**
     * Determine the cube state that will undo the current cube state.
     * This allows you to work backwards from a scrambled cube in a bidirectional search,
     * and to build the inverse of a move (for example, U' from U). This cube is not modified.
     * @return A new cube state that reverses the effects of this one.
     */
    public CubeState inverse() {
        CubeState result = new CubeState();

        for (int i = 0; i < CORNERS; i++) {
            byte src = this.cornerPerm[i];
            result.cornerPerm[src] = (byte) i;
            result.cornerOri[src] = (byte) ((3 - this.cornerOri[i]) % 3);
        }

        for (int i = 0; i < EDGES; i++) {
            byte src = this.edgePerm[i];
            result.edgePerm[src] = (byte) i;
            result.edgeOri[src] = this.edgeOri[i];
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CubeState)) return false;
        CubeState other = (CubeState) obj;
        return Arrays.equals(this.cornerPerm, other.cornerPerm)
                && Arrays.equals(this.cornerOri, other.cornerOri)
                && Arrays.equals(this.edgePerm, other.edgePerm)
                && Arrays.equals(this.edgeOri, other.edgeOri);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(this.cornerPerm);
        result = 31 * result + Arrays.hashCode(this.cornerOri);
        result = 31 * result + Arrays.hashCode(this.edgePerm);
        result = 31 * result + Arrays.hashCode(this.edgeOri);
        return result;
    }

    public static final CubeState SOLVED = new CubeState();
    public boolean isSolved() {
        return this.equals(SOLVED);
    }

    @Override
    public String toString() {
        return "cornerPerm=" + Arrays.toString(cornerPerm)
                + ", cornerOri=" + Arrays.toString(cornerOri)
                + ", edgePerm=" + Arrays.toString(edgePerm)
                + ", edgeOri=" + Arrays.toString(edgeOri);
    }
}