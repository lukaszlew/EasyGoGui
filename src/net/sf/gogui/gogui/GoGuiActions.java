//----------------------------------------------------------------------------
// $Id$
//----------------------------------------------------------------------------

package net.sf.gogui.gogui;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import net.sf.gogui.game.ConstClock;
import net.sf.gogui.game.ConstGame;
import net.sf.gogui.game.ConstNode;
import net.sf.gogui.game.NodeUtil;
import net.sf.gogui.go.GoColor;
import net.sf.gogui.gui.ConstGuiBoard;
import net.sf.gogui.gui.GameTreePanel;
import net.sf.gogui.gui.GuiUtil;
import net.sf.gogui.util.Platform;

/** Actions used in the GoGui tool bar and menu bar.
    This class has a cyclic dependency with class GoGui, however the
    dependency has a simple structure. The class contains actions that wrap a
    call to public functions of GoGui and associate a name, icon, description
    and accelerator key. There are also update functions that are used to
    enable actions or add additional information to their descriptions
    depending on the state of GoGui as far as it is accessible through public
    functions.
*/
public class GoGuiActions
{
    public final GoGuiAction m_actionAbout =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionAbout(); } },
             "About", "Show information about GoGui, Go program and Java");

    public final GoGuiAction m_actionAddBookmark =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionAddBookmark(); } },
             "Add Bookmark",
             "Add bookmark at current position in current file",
             KeyEvent.VK_B);

    public final GoGuiAction m_actionAttachProgram =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionAttachProgram(); } },
             "Attach Program...", "Attach Go program to current game",
             KeyEvent.VK_A, null);

    public final GoGuiAction m_actionBackToMainVariation =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionBackToMainVariation(); } },
             "Back to Main Variation", "Go back to main variation",
             KeyEvent.VK_M);

    public final GoGuiAction m_actionBackward =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionBackward(1); } },
             "Backward", "Go one move backward", KeyEvent.VK_LEFT,
             "gogui-previous");

    public final GoGuiAction m_actionBackwardTen =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionBackward(10); } },
             "Backward 10", "Go ten moves backward",
             KeyEvent.VK_LEFT, getShortcut() | ActionEvent.SHIFT_MASK, null);

    public final GoGuiAction m_actionBeginning =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionBeginning(); } },
             "Beginning", "Go to beginning of game", KeyEvent.VK_HOME,
             "gogui-first");

    public final GoGuiAction m_actionBoardSize9 =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionBoardSize(9); } },
             "9", "Change board size to 9x9");

    public final GoGuiAction m_actionBoardSize11 =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionBoardSize(11); } },
             "11", "Change board size to 11x11");

    public final GoGuiAction m_actionBoardSize13 =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionBoardSize(13); } },
             "13", "Change board size to 13x13");

    public final GoGuiAction m_actionBoardSize15 =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionBoardSize(15); } },
             "15", "Change board size to 15x15");

    public final GoGuiAction m_actionBoardSize17 =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionBoardSize(17); } },
             "17", "Change board size to 17x17");

    public final GoGuiAction m_actionBoardSize19 =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionBoardSize(19); } },
             "19", "Change board size to 19x19");

    public final GoGuiAction m_actionBoardSizeOther =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionBoardSizeOther(); } },
             "Other", "Change board size to other values");

    public final GoGuiAction m_actionClockHalt =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionClockHalt(); } },
             "Halt", "Halt clock");

    public final GoGuiAction m_actionClockResume =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionClockResume(); } },
             "Resume", "Resume clock");

    public final GoGuiAction m_actionClockRestore =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionClockRestore(); } },
             "Restore", "Restore clock to time stored at current position");

    public final GoGuiAction m_actionComputerBlack =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionComputerColor(true, false); } },
             "Black", "Make computer play Black");

    public final GoGuiAction m_actionComputerBoth =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionComputerColor(true, true); } },
             "Both", "Make computer play both sides");

    public final GoGuiAction m_actionComputerNone =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionComputerColor(false, false); } },
             "None", "Make computer play no side");

    public final GoGuiAction m_actionComputerWhite =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionComputerColor(false, true); } },
             "White", "Make computer play White");

    public final GoGuiAction m_actionEditBookmarks =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionEditBookmarks(); } },
             "Edit Bookmarks...", "Edit list of bookmarks");

    public final GoGuiAction m_actionGoto =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionGoto(); } },
             "Go to Move...", "Go to position after a move number",
             KeyEvent.VK_G);

    public final GoGuiAction m_actionGotoVariation =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionGotoVariation(); } },
             "Go to Variation...", "Go to beginning of a variation");

    public final GoGuiAction m_actionDeleteSideVariations =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionDeleteSideVariations(); } },
             "Delete side variations",
             "Delete all variations but the main variation");

    public final GoGuiAction m_actionDetachProgram =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionDetachProgram(); } },
             "Detach Program...",
             "Detach Go program from current game and terminate it");

    public final GoGuiAction m_actionDocumentation =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionDocumentation(); } },
             "GoGui Documentation", "Open GoGui manual", KeyEvent.VK_F1,
             getFunctionKeyShortcut());

    public final GoGuiAction m_actionEnd =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionEnd(); } },
             "End", "Go to end of game", KeyEvent.VK_END, "gogui-last");

    public final GoGuiAction m_actionExportSgfPosition =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionExportSgfPosition(); } },
             "SGF Position...", "Export position as SGF file");

    public final GoGuiAction m_actionExportLatexMainVariation =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionExportLatexMainVariation(); } },
             "LaTeX Main Variation...",
             "Export main variation as LaTeX PSGO file");

    public final GoGuiAction m_actionExportLatexPosition =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionExportLatexPosition(); } },
             "LaTeX Position...", "Export position as LaTeX PSGO file");

    public final GoGuiAction m_actionExportTextPosition =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionExportTextPosition(); } },
             "Text Position...", "Export position as text diagram");

    public final GoGuiAction m_actionExportTextPositionToClipboard =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionExportTextPositionToClipboard(); } },
             "Text Position to Clipboard",
             "Export position as text diagram to clipboard");

    public final GoGuiAction m_actionFind =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionFind(); } },
             "Find in Comments...", "Search for matching text in comments",
             KeyEvent.VK_F);

    public final GoGuiAction m_actionFindNext =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionFindNext(); } },
             "Find Next", "Search for next match in comments",
             KeyEvent.VK_F3, getFunctionKeyShortcut());

    public final GoGuiAction m_actionForward =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionForward(1); } },
             "Forward", "Go one move forward", KeyEvent.VK_RIGHT,
             "gogui-next");

    public final GoGuiAction m_actionForwardTen =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionForward(10); } },
             "Forward 10", "Go ten moves forward", KeyEvent.VK_RIGHT,
             getShortcut() | ActionEvent.SHIFT_MASK, null);

    public final GoGuiAction m_actionGameInfo =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionGameInfo(); } },
             "Game Info", "Show and edit game information", KeyEvent.VK_I);

    public final GoGuiAction m_actionShellSave =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionShellSave(); } },
             "Save Log...", "Save GTP history");

    public final GoGuiAction m_actionShellSaveCommands =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionShellSaveCommands(); } },
             "Save Commands...", "Save history of GTP commands");

    public final GoGuiAction m_actionShellSendFile =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionShellSendFile(); } },
             "Send File...", "Send file with GTP commands");

    public final GoGuiAction m_actionHandicapNone =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionHandicap(0); } },
             "None", "Do not use handicap stones");

    public final GoGuiAction m_actionHandicap2 =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionHandicap(2); } },
             "2", "Use two handicap stones");

    public final GoGuiAction m_actionHandicap3 =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionHandicap(3); } },
             "3", "Use three handicap stones");

    public final GoGuiAction m_actionHandicap4 =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionHandicap(4); } },
             "4", "Use four handicap stones");

    public final GoGuiAction m_actionHandicap5 =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionHandicap(5); } },
             "5", "Use five handicap stones");

    public final GoGuiAction m_actionHandicap6 =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionHandicap(6); } },
             "6", "Use six handicap stones");

    public final GoGuiAction m_actionHandicap7 =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionHandicap(7); } },
             "7", "Use seven handicap stones");

    public final GoGuiAction m_actionHandicap8 =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionHandicap(8); } },
             "8", "Use eight handicap stones");

    public final GoGuiAction m_actionHandicap9 =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionHandicap(9); } },
             "9", "Use nine handicap stones");

    public final GoGuiAction m_actionImportTextPosition =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionImportTextPosition(); } },
             "Text Position...",
             "Import position as text diagram from file");

    public final GoGuiAction m_actionImportTextPositionFromClipboard =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionImportTextPositionFromClipboard(); } },
             "Text Position from Clipboard",
             "Import position as text diagram from clipboard");

    public final GoGuiAction m_actionInterrupt =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionInterrupt(); } },
             "Interrupt", "Interrupt program", KeyEvent.VK_ESCAPE, 0,
             "gogui-interrupt");

    public final GoGuiAction m_actionKeepOnlyPosition =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionKeepOnlyPosition(); } },
             "Keep only Position",
             "Delete variations and moves and keep only the current position");

    public final GoGuiAction m_actionMakeMainVariation =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionMakeMainVariation(); } },
             "Make Main Variation",
             "Make current variation the main variation");

    public final GoGuiAction m_actionNextEarlierVariation =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionNextEarlierVariation(); } },
             "Next Earlier Variation", "Go to next earlier variation",
             KeyEvent.VK_DOWN, getShortcut() | ActionEvent.SHIFT_MASK);

    public final GoGuiAction m_actionNextVariation =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionNextVariation(); } },
             "Next Variation", "Go to next variation", KeyEvent.VK_DOWN,
             "gogui-down");

    public final GoGuiAction m_actionNewGame =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionNewGame(); } },
             "New Game", "Clear board and begin new game", "gogui-newgame");

    public final GoGuiAction m_actionOpen =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionOpen(); } },
             "Open...", "Open game", KeyEvent.VK_O, "document-open");

    public final GoGuiAction m_actionPass =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionPass(); } },
             "Pass", "Play a pass", KeyEvent.VK_F2,
             getFunctionKeyShortcut(), "gogui-pass");

    public final GoGuiAction m_actionPlay =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionPlay(false); } },
             "Play", "Make computer play", KeyEvent.VK_F5,
             getFunctionKeyShortcut(), "gogui-play");

    public final GoGuiAction m_actionPlaySingleMove =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionPlay(true); } },
             "Play Single Move",
             "Make computer play a move (do not change computer color)",
             KeyEvent.VK_F5,
             getFunctionKeyShortcut() | ActionEvent.SHIFT_MASK);

    public final GoGuiAction m_actionPreviousEarlierVariation =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionPreviousEarlierVariation(); } },
             "Previous Earlier Variation", "Go to previous earlier variation",
             KeyEvent.VK_UP, getShortcut() | ActionEvent.SHIFT_MASK);

    public final GoGuiAction m_actionPreviousVariation =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionPreviousVariation(); } },
             "Previous Variation", "Go to previous variation", KeyEvent.VK_UP,
             "gogui-up");

    public final GoGuiAction m_actionPrint =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionPrint(); } },
             "Print...", "Print current position", KeyEvent.VK_P, null);

    public final GoGuiAction m_actionSave =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionSave(); } },
             "Save", "Save game", KeyEvent.VK_S, "document-save");

    public final GoGuiAction m_actionSaveAs =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionSaveAs(); } },
             "Save As...", "Save game", "document-save-as");

    public final GoGuiAction m_actionScore =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionScore(); } },
             "Score", "Score position");

    public final GoGuiAction m_actionSetup =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionSetup(); } },
             "Setup", "Enter or leave setup mode");

    public final GoGuiAction m_actionSetupBlack =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionSetupColor(GoColor.BLACK); } },
             "Setup Black", "Change setup color to Black");

    public final GoGuiAction m_actionSetupWhite =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionSetupColor(GoColor.WHITE); } },
             "Setup White", "Change setup color to White");

    public final GoGuiAction m_actionToggleAutoNumber =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionToggleAutoNumber(); } },
             "Auto Number", null);

    public final GoGuiAction m_actionToggleBeepAfterMove =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionToggleBeepAfterMove(); } },
             "Beep After Move", "Beep after computer played a move");

    public final GoGuiAction m_actionToggleCompletion =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionToggleCompletion(); } },
             "Popup Completions", null);

    public final GoGuiAction m_actionToggleCommentMonoFont =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionToggleCommentMonoFont(); } },
             "Monospace Comment Font", "Use fixed width font for comment");

    public final GoGuiAction m_actionToggleShowAnalyzeDialog =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionToggleShowAnalyzeDialog(); } },
             "Show Analyze", "Show window with analyze commands",
             KeyEvent.VK_F9, getFunctionKeyShortcut());

    public final GoGuiAction m_actionToggleShowCursor =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionToggleShowCursor(); } },
             "Show Cursor", "Show cursor on board");

    public final GoGuiAction m_actionToggleShowGrid =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionToggleShowGrid(); } },
             "Show Grid", "Show board grid labels");

    public final GoGuiAction m_actionToggleShowInfoPanel =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionToggleShowInfoPanel(); } },
             "Show Info Panel",
             "Show panel with comment and game information");

    public final GoGuiAction m_actionToggleShowLastMove =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionToggleShowLastMove(); } },
             "Show Last Move", "Mark last move on board");

    public final GoGuiAction m_actionToggleShowShell =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionToggleShowShell(); } },
             "Show Shell", "Show GTP shell window",
             KeyEvent.VK_F8, getFunctionKeyShortcut());

    public final GoGuiAction m_actionToggleShowSubtreeSizes =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionToggleShowSubtreeSizes(); } },
             "Show Subtree Sizes", null);

    public final GoGuiAction m_actionToggleShowToolbar =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionToggleShowToolbar(); } },
             "Show Tool Bar", "Show tool bar");

    public final GoGuiAction m_actionToggleShowTree =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionToggleShowTree(); } },
             "Show Tree", "Show game tree window",
             KeyEvent.VK_F7, getFunctionKeyShortcut());

    public final GoGuiAction m_actionToggleShowVariations =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionToggleShowVariations(); } },
             "Show Variations",
             "Label children moves with letters on board");

    public final GoGuiAction m_actionToggleTimeStamp =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionToggleTimeStamp(); } },
             "Timestamp", null);

    public final GoGuiAction m_actionTreeLabelsNumber =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionTreeLabels(GameTreePanel.LABEL_NUMBER); } },
             "Move Number", null);

    public final GoGuiAction m_actionTreeLabelsMove =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionTreeLabels(GameTreePanel.LABEL_MOVE); } },
             "Move", null);

    public final GoGuiAction m_actionTreeLabelsNone =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionTreeLabels(GameTreePanel.LABEL_NONE); } },
             "None", null);

    public final GoGuiAction m_actionTreeSizeLarge =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionTreeSize(GameTreePanel.SIZE_LARGE); } },
             "Large", null);

    public final GoGuiAction m_actionTreeSizeNormal =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionTreeSize(GameTreePanel.SIZE_NORMAL); } },
             "Normal", null);

    public final GoGuiAction m_actionTreeSizeSmall =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionTreeSize(GameTreePanel.SIZE_SMALL); } },
             "Small", null);

    public final GoGuiAction m_actionTreeSizeTiny =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionTreeSize(GameTreePanel.SIZE_TINY); } },
             "Tiny", null);

    public final GoGuiAction m_actionTruncate =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionTruncate(); } },
             "Truncate", "Truncate subtree including this position");

    public final GoGuiAction m_actionTruncateChildren =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionTruncateChildren(); } },
             "Truncate Children", "Truncate all children of this position");

    public final GoGuiAction m_actionQuit =
        new GoGuiAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m_goGui.actionQuit(); } },
             "Quit", "Quit GoGui", KeyEvent.VK_Q, null);

    public GoGuiActions(GoGui goGui)
    {
        m_goGui = goGui;
    }

    public void update()
    {
        ConstGame game = m_goGui.getGame();
        int handicap = m_goGui.getHandicapDefault();
        boolean setupMode = m_goGui.isInSetupMode();
        GoColor setupColor = m_goGui.getSetupColor();
        File file = m_goGui.getFile();
        boolean isModified = m_goGui.isModified();
        ConstGuiBoard guiBoard = m_goGui.getGuiBoard();
        ConstNode node = game.getCurrentNode();
        boolean hasFather = (node.getFatherConst() != null);
        boolean hasChildren = (node.getNumberChildren() > 0);
        boolean hasNextVariation = (NodeUtil.getNextVariation(node) != null);
        boolean hasPreviousVariation =
            (NodeUtil.getPreviousVariation(node) != null);
        boolean hasNextEarlierVariation =
            (NodeUtil.getNextEarlierVariation(node) != null);
        boolean hasPrevEarlierVariation =
            (NodeUtil.getPreviousEarlierVariation(node) != null);
        boolean isInMain = NodeUtil.isInMainVariation(node);
        boolean treeHasVariations = game.getTree().hasVariations();
        boolean isProgramAttached = m_goGui.isProgramAttached();
        boolean computerBlack = m_goGui.isComputerColor(GoColor.BLACK);
        boolean computerWhite = m_goGui.isComputerColor(GoColor.WHITE);
        boolean hasPattern = (m_goGui.getPattern() != null);
        ConstClock clock = game.getClock();
        int boardSize = game.getSize();
        m_actionBackToMainVariation.setEnabled(! isInMain);
        m_actionBackward.setEnabled(hasFather);
        m_actionBackwardTen.setEnabled(hasFather);
        m_actionBeginning.setEnabled(hasFather);
        m_actionBoardSize9.setSelected(boardSize == 9);
        m_actionBoardSize11.setSelected(boardSize == 11);
        m_actionBoardSize13.setSelected(boardSize == 13);
        m_actionBoardSize15.setSelected(boardSize == 15);
        m_actionBoardSize17.setSelected(boardSize == 17);
        m_actionBoardSize19.setSelected(boardSize == 19);
        m_actionBoardSizeOther.setSelected(boardSize < 9 || boardSize > 19
                                           || boardSize % 2 == 0);
        m_actionClockHalt.setEnabled(clock.isRunning());
        m_actionClockResume.setEnabled(! clock.isRunning());
        m_actionClockRestore.setEnabled(NodeUtil.canRestoreTime(node, clock));
        m_actionComputerBlack.setEnabled(isProgramAttached);
        m_actionComputerBlack.setSelected(computerBlack && ! computerWhite);
        m_actionComputerBoth.setEnabled(isProgramAttached);
        m_actionComputerBoth.setSelected(computerBlack && computerWhite);
        m_actionComputerNone.setEnabled(isProgramAttached);
        m_actionComputerNone.setSelected(! computerBlack && ! computerWhite);
        m_actionComputerWhite.setEnabled(isProgramAttached);
        m_actionComputerWhite.setSelected(! computerBlack && computerWhite);
        m_actionDeleteSideVariations.setEnabled(isInMain && treeHasVariations);
        m_actionDetachProgram.setEnabled(isProgramAttached);
        m_actionEnd.setEnabled(hasChildren);
        m_actionFindNext.setEnabled(hasPattern);
        m_actionForward.setEnabled(hasChildren);
        m_actionForwardTen.setEnabled(hasChildren);
        m_actionGoto.setEnabled(hasFather || hasChildren);
        m_actionGotoVariation.setEnabled(hasFather || hasChildren);
        m_actionHandicapNone.setSelected(handicap == 0);
        m_actionHandicap2.setSelected(handicap == 2);
        m_actionHandicap3.setSelected(handicap == 3);
        m_actionHandicap4.setSelected(handicap == 4);
        m_actionHandicap5.setSelected(handicap == 5);
        m_actionHandicap6.setSelected(handicap == 6);
        m_actionHandicap7.setSelected(handicap == 7);
        m_actionHandicap8.setSelected(handicap == 8);
        m_actionHandicap9.setSelected(handicap == 9);
        m_actionInterrupt.setEnabled(isProgramAttached);
        m_actionKeepOnlyPosition.setEnabled(hasFather || hasChildren);
        m_actionMakeMainVariation.setEnabled(! isInMain);
        m_actionNextEarlierVariation.setEnabled(hasNextEarlierVariation);
        m_actionNextVariation.setEnabled(hasNextVariation);
        m_actionPlay.setEnabled(isProgramAttached);
        m_actionPreviousVariation.setEnabled(hasPreviousVariation);
        m_actionPreviousEarlierVariation.setEnabled(hasPrevEarlierVariation);
        m_actionSetupBlack.setEnabled(setupMode);
        m_actionSetupWhite.setEnabled(setupMode);
        m_actionSetupBlack.setSelected(setupColor == GoColor.BLACK);
        m_actionSetupWhite.setSelected(setupColor == GoColor.WHITE);
        m_actionShellSave.setEnabled(isProgramAttached);
        m_actionShellSaveCommands.setEnabled(isProgramAttached);
        m_actionShellSendFile.setEnabled(isProgramAttached);
        m_actionToggleAutoNumber.setSelected(m_goGui.getAutoNumber());
        m_actionToggleBeepAfterMove.setEnabled(isProgramAttached);
        m_actionToggleBeepAfterMove.setSelected(m_goGui.getBeepAfterMove());
        boolean commentMonoFont = m_goGui.getCommentMonoFont();
        m_actionToggleCommentMonoFont.setSelected(commentMonoFont);
        m_actionToggleCompletion.setSelected(m_goGui.getCompletion());
        boolean isAnalyzeDialogShown = m_goGui.isAnalyzeDialogShown();
        m_actionToggleShowAnalyzeDialog.setSelected(isAnalyzeDialogShown);
        m_actionToggleShowCursor.setSelected(guiBoard.getShowCursor());
        m_actionToggleShowGrid.setSelected(guiBoard.getShowGrid());
        m_actionToggleShowInfoPanel.setSelected(m_goGui.isInfoPanelShown());
        m_actionToggleShowLastMove.setSelected(m_goGui.getShowLastMove());
        m_actionToggleShowShell.setSelected(m_goGui.isShellShown());
        boolean showSubtreeSizes = m_goGui.getShowSubtreeSizes();
        m_actionToggleShowSubtreeSizes.setSelected(showSubtreeSizes);
        m_actionToggleShowToolbar.setSelected(m_goGui.isToolbarShown());
        m_actionToggleShowTree.setSelected(m_goGui.isTreeShown());
        m_actionToggleShowVariations.setSelected(m_goGui.getShowVariations());
        m_actionToggleTimeStamp.setSelected(m_goGui.getTimeStamp());
        m_actionTreeLabelsNumber.setSelected(
                    m_goGui.getTreeLabels() == GameTreePanel.LABEL_NUMBER);
        m_actionTreeLabelsMove.setSelected(
                    m_goGui.getTreeLabels() == GameTreePanel.LABEL_MOVE);
        m_actionTreeLabelsNone.setSelected(
                    m_goGui.getTreeLabels() == GameTreePanel.LABEL_NONE);
        m_actionTreeSizeLarge.setSelected(
                    m_goGui.getTreeSize() == GameTreePanel.SIZE_LARGE);
        m_actionTreeSizeNormal.setSelected(
                    m_goGui.getTreeSize() == GameTreePanel.SIZE_NORMAL);
        m_actionTreeSizeSmall.setSelected(
                    m_goGui.getTreeSize() == GameTreePanel.SIZE_SMALL);
        m_actionTreeSizeTiny.setSelected(
                    m_goGui.getTreeSize() == GameTreePanel.SIZE_TINY);
        m_actionTruncate.setEnabled(hasFather);
        m_actionTruncateChildren.setEnabled(hasChildren);
        updateFile(file, isModified);
    }

    private final GoGui m_goGui;

    /** Get shortcut modifier for function keys.
        Returns 0, unless platform is Mac.
    */
    private static int getFunctionKeyShortcut()
    {
        if (Platform.isMac())
            return getShortcut();
        return 0;
    }

    private static int getShortcut()
    {
        return GoGuiAction.getShortcut();
    }

    private void updateFile(File file, boolean isModified)
    {
        String desc = "Save game";
        if (file != null)
            desc = desc + " (" + file + ")";
        m_actionSave.setDescription(desc);
        m_actionSave.setEnabled(file != null && isModified);
    }
}

class GoGuiAction
    extends AbstractAction
{
    public GoGuiAction(ActionListener listener, String name, String desc)
    {
        this(listener, name, desc, null, 0, null);
    }
    
    public GoGuiAction(ActionListener listener, String name, String desc,
                       String icon)
    {
        this(listener, name, desc, null, 0, icon);
    }

    public GoGuiAction(ActionListener listener, String name, String desc,
                       int accel, String icon)
    {
        this(listener, name, desc, new Integer(accel), getShortcut(), icon);
    }

    public GoGuiAction(ActionListener listener, String name, String desc,
                       int accel)
    {
        this(listener, name, desc, new Integer(accel), getShortcut(), null);
    }

    public GoGuiAction(ActionListener listener, String name, String desc,
                       int accel, int modifier, String icon)
    {
        this(listener, name, desc, new Integer(accel), modifier, icon);
    }

    public GoGuiAction(ActionListener listener, String name, String desc,
                       int accel, int modifier)
    {
        this(listener, name, desc, new Integer(accel), modifier, null);
    }

    public GoGuiAction(ActionListener listener, String name, String desc,
                       Integer accel, int modifier, String icon)
    {
        m_listener = listener;
        putValue(AbstractAction.NAME, name);
        setDescription(desc);
        if (accel != null)
        {
            KeyStroke keyStroke = getKeyStroke(accel.intValue(), modifier);
            putValue(AbstractAction.ACCELERATOR_KEY, keyStroke);
        }
        if (icon != null)
            putValue(AbstractAction.SMALL_ICON, GuiUtil.getIcon(icon, name));
    }

    public void actionPerformed(ActionEvent e)
    {
        m_listener.actionPerformed(e);
    }

    public static int getShortcut()
    {
        return Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
    }

    public void setDescription(String desc)
    {
        putValue(AbstractAction.SHORT_DESCRIPTION, desc);
    }

    public void setSelected(boolean selected)
    {
        putValue("selected", Boolean.valueOf(selected));
    }

    /** Serial version to suppress compiler warning.
        Contains a marker comment for serialver.sourceforge.net
    */
    private static final long serialVersionUID = 0L; // SUID

    private final ActionListener m_listener;

    private static KeyStroke getKeyStroke(int keyCode, int modifier)
    {
        return KeyStroke.getKeyStroke(keyCode, modifier);
    }
}