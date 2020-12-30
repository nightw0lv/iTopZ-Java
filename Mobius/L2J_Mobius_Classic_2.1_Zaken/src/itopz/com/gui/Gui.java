/*
 * Copyright (c) 2021 iTopZ
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package itopz.com.gui;

import itopz.com.Configurations;
import itopz.com.model.entity.ITOPZ_GLOBAL;
import itopz.com.util.URL;
import itopz.com.util.Utilities;

import javax.swing.*;
import java.awt.*;

/**
 * @Author Nightwolf
 * iToPz Discord: https://discord.gg/KkPms6B5aE
 * @Author Rationale
 * Base structure credits goes on Rationale Discord: Rationale#7773
 *
 * Vote Donation System
 * Script website: https://itopz.com/
 * Script version: 1.0
 * Pack Support: Mobius 2.1 Zaken
 *
 * Personal Donate Panels: https://www.denart-designs.com/
 * Free Donate panel: https://itopz.com/
 */
public class Gui extends JFrame
{
    // console
    private JTextArea console;

    // menu
    private JMenu menuServer, menuDonate, menuAbout;

    // label
    private JLabel modeLabel, statsLabel;

    /**
	* Constructor load gui
	*/
    Gui()
    {
        startGui();
        addMenuItems();
    }

    /**
     * set up all the GUI components
     */
    public void startGui()
    {
        // set menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // set menu item
        menuServer = new JMenu("Server");
        menuBar.add(menuServer);
        menuDonate = new JMenu("Donates");
        menuBar.add(menuDonate);
        menuAbout = new JMenu("About");
        menuBar.add(menuAbout);

        modeLabel = new JLabel("Loading..", JLabel.LEFT);
        modeLabel.setText(addModeInfo(Configurations.ITOPZ_SERVER_API_KEY));
        modeLabel.setBorder(BorderFactory.createEmptyBorder(2, 10, 10, 10));

        // set statistics label info
        statsLabel = new JLabel("Waiting for statistics info.", JLabel.LEFT);
        statsLabel.setBorder(BorderFactory.createEmptyBorder(2, 10, 0, 10));

        // Create a JTextArea
        console = new JTextArea("Server info started.");
        console.setFont(new Font(Configurations.ITOPZ_CONSOLE_FONT, Font.BOLD, Configurations.ITOPZ_CONSOLE_SIZE));
        console.setLineWrap(true); // wrap line
        console.setWrapStyleWord(true); // wrap line at word boundary
        console.setBackground(new Color(Configurations.ITOPZ_CONSOLE_COLOR_R, Configurations.ITOPZ_CONSOLE_COLOR_G, Configurations.ITOPZ_CONSOLE_COLOR_B)); // light blue

        // Wrap the JTextArea inside a JScrollPane
        JScrollPane tAreaScrollPane = new JScrollPane(console);
        tAreaScrollPane.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        tAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Setup the content-pane of JFrame in BorderLayout
        Container cp = this.getContentPane();
        cp.setBackground(Color.BLACK);
        cp.setLayout(new BorderLayout(5, 5));
        cp.add(statsLabel, BorderLayout.NORTH);
        cp.add(modeLabel, BorderLayout.SOUTH);
        cp.add(tAreaScrollPane, BorderLayout.CENTER);

        // Setup basic window settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("iToPz server console.");
        setSize(Configurations.ITOPZ_CONSOLE_WIDTH, Configurations.ITOPZ_CONSOLE_HEIGHT);
        setVisible(true);
    }

    /**
     *  set mode text border
     *
     * @param mode string
     * @return string
     */
    public String addModeInfo(String mode)
    {
        String s;
        // set mode text border
        if (mode.equals("DEMO"))
        {
            modeLabel.setForeground(Color.ORANGE);
            s = "Mode: TEST";
            modeLabel.setToolTipText("<html>Change your config from DEMO into an actual API Key obtain from itopz</html>");
        }
        else if (mode.length() >= 30)
        {
            modeLabel.setForeground(Color.GREEN);
            s = "Mode: LIVE";
            modeLabel.setToolTipText("<html>Live mode</html>");
        }
        else
        {
            modeLabel.setForeground(Color.RED);
            s = "Mode: UNKNOWN";
            modeLabel.setToolTipText("<html>Use DEMO or an actual API Key from itopz</html>");
        }
        return s;
    }

    /**
     * update statistics
     *
     * @param serverVotes int
     * @param serverRank int
     * @param serverNextRank int
     * @param serverNeededVotes int
     */
    public void UpdateStats(int serverVotes, int serverRank, int serverNextRank, int serverNeededVotes)
    {
        statsLabel.setText("Server Votes:" + serverVotes + " Rank:" + serverRank + " Next Rank(" + serverNextRank + ") need: " + serverNeededVotes + "votes.");
        if (serverVotes < 0)
            statsLabel.setForeground(Color.RED);
        else
            statsLabel.setForeground(Color.GREEN);
        if (Configurations.ITOPZ_VOTE_CHECK_DELAY > 60)
            statsLabel.setToolTipText("<html>Statistics up date every " + Configurations.ITOPZ_VOTE_CHECK_DELAY / 60 + " minutes</html>");
        else
            statsLabel.setToolTipText("<html>Statistics up date every " + Configurations.ITOPZ_VOTE_CHECK_DELAY + " seconds</html>");
    }

    /**
     * Create Menu items
     */
    public void addMenuItems()
    {
        // run global reward
        JMenuItem run_global_reward = new JMenuItem("Run Global");
        run_global_reward.addActionListener(al-> new ITOPZ_GLOBAL().load());
        run_global_reward.setToolTipText("<html>Run global reward</html>");
        menuServer.add(run_global_reward);

        // server info
        JMenuItem server_info = new JMenuItem("Server info");
        server_info.addActionListener(al-> Utilities.openUrl(URL.ITOPZ_SERVER_URL.toString()));
        server_info.setToolTipText("<html>Visit server info at iTopZ</html>");
        menuServer.add(server_info);

        // donate settings
        JMenuItem donate_settings = new JMenuItem("Donate Settings");
        donate_settings.addActionListener(al-> Utilities.openUrl(URL.ITOPZ_URL.toString() + "donate_settings/" + Configurations.ITOPZ_SERVER_ID));
        donate_settings.setToolTipText("<html>Visit donate settings at iTopZ</html>");
        menuDonate.add(donate_settings);

        // donate payments
        JMenuItem donate_payments = new JMenuItem("Donate Payments");
        donate_payments.addActionListener(al-> Utilities.openUrl(URL.ITOPZ_URL.toString() + "donate_payments/" + Configurations.ITOPZ_SERVER_ID));
        donate_payments.setToolTipText("<html>Visit donate payments at iTopZ</html>");
        menuDonate.add(donate_payments);

        // donate announce
        JMenuItem donate_inform = new JMenuItem("Donate Announce link");
        donate_inform.addActionListener(al->
        {
            Utilities.announce("Donate for our server at " + URL.ITOPZ_URL.toString() + "donate/" + Configurations.ITOPZ_SERVER_ID);
            ConsoleWrite("Announce send to server");
        });
        donate_inform.setToolTipText("<html>Inform all players about donate link</html>");
        menuDonate.add(donate_inform);

        // about itopz
        JMenuItem about = new JMenuItem("iTopZ");
        about.addActionListener(al-> Utilities.openUrl(URL.ITOPZ_SERVER_URL.toString()));
        about.setToolTipText("<html>Visit iTopZ</html>");
        menuAbout.add(about);

        // about itopz discord
        JMenuItem discord = new JMenuItem("Discord Server");
        discord.addActionListener(al-> Utilities.openUrl(URL.DISCORD.toString()));
        discord.setToolTipText("<html>Visit Discord</html>");
        menuAbout.add(discord);

        // about denart designs
        JMenuItem denart_designs = new JMenuItem("GFX Designer");
        denart_designs.addActionListener(al-> Utilities.openUrl(URL.DENART_DESIGNS.toString()));
        denart_designs.setToolTipText("<html>Visit Denart Designs</html>");
        menuAbout.add(denart_designs);
    }

    /**
     * write console
     *
     * @param message String
     */
    public void ConsoleWrite(String message)
    {
        // console append message
        console.append("\n" + message);
        // follow scroll
        console.setCaretPosition(console.getDocument().getLength());
    }

    public static Gui getInstance()
    {
        return SingletonHolder._instance;
    }

    private static class SingletonHolder
    {
        protected static final Gui _instance = new Gui();
    }
}
