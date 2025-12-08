/**
 * This package contains classes and interfaces related to the Tetris bricks and their generation.
 * <p>
 * It defines the structure, behavior, and rotation logic of all brick types in the game,
 * as well as generators to produce random bricks during gameplay.
 * <ul>
 *     <li>{@link com.comp2042.logic.bricks.Brick} - Interface representing a generic Tetris brick.</li>
 *     <li>{@link com.comp2042.logic.bricks.BrickGenerator} - Interface for generating new bricks.</li>
 *     <li>{@link com.comp2042.logic.bricks.RandomBrickGenerator} - Implements Brick generator to generate random bricks.</li>
 *     <li>{@link com.comp2042.logic.bricks.JBrick} - Represents the J-shaped Tetris brick.</li>
 *     <li>{@link com.comp2042.logic.bricks.SBrick} - Represents the S-shaped Tetris brick.</li>
 *     <li>{@link com.comp2042.logic.bricks.TBrick} - Represents the T-shaped Tetris brick.</li>
 *     <li>{@link com.comp2042.logic.bricks.OBrick} - Represents the O-shaped Tetris brick.</li>
 *     <li>{@link com.comp2042.logic.bricks.ZBrick} - Represents the Z-shaped Tetris brick.</li>
 *     <li>{@link com.comp2042.logic.bricks.LBrick} - Represents the L-shaped Tetris brick.</li>
 *     <li>{@link com.comp2042.logic.bricks.IBrick} - Represents the I-shaped Tetris brick.</li>
 * </ul>
 * <p>
 * This package provides the core building blocks for Tetris gameplay, defining
 * both the shapes and rotation matrices of all bricks and enabling the game
 * to generate new bricks dynamically.
 */
package com.comp2042.logic.bricks;
