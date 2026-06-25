import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.swing.Timer;

public class IPLAuctionSystem {

    static java.util.List<String[]> bidHistory = new ArrayList<>();
    static String[] teams = {"CSK","MI","RCB","SRH","KKR","DC","RR","PBKS","GT","LSG"};
    static String[] players = {
        "Virat Kohli","Rohit Sharma","MS Dhoni","Jasprit Bumrah","Hardik Pandya",
        "KL Rahul","Shubman Gill","Ruturaj Gaikwad","Suryakumar Yadav","Ravindra Jadeja",
        "R Ashwin","Yuzvendra Chahal","Mohammed Shami","Mohammed Siraj","Arshdeep Singh",
        "Ishan Kishan","Sanju Samson","Rinku Singh","Tilak Varma","Shivam Dube"
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(IPLAuctionSystem::showLogin);
    }

    static void showLogin() {
        JFrame f = new JFrame("Online Auction/Bidding System");
        f.setSize(500, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel p = new JPanel(new GridLayout(5,1,10,10));
        JTextField user = new JTextField();
        JPasswordField pass = new JPasswordField();

        p.add(new JLabel("Username"));
        p.add(user);
        p.add(new JLabel("Password"));
        p.add(pass);

        JButton login = new JButton("Login");
        login.addActionListener(e -> {
            if(user.getText().equals("admin") &&
                    String.valueOf(pass.getPassword()).equals("123")) {
                f.dispose();
                dashboard();
            } else {
                JOptionPane.showMessageDialog(f,"Invalid Username or Password");
            }
        });

        p.add(login);
        f.add(p);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    static void dashboard() {
        JFrame f = new JFrame("IPL Auction Dashboard");
        f.setSize(800,600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel left = new JPanel(new GridLayout(4,1,10,10));

        JButton teamsBtn = new JButton("Teams");
        JButton playersBtn = new JButton("Players");
        JButton auctionBtn = new JButton("Auction");
        JButton resultBtn = new JButton("Results");

        teamsBtn.addActionListener(e -> showTeams());
        playersBtn.addActionListener(e -> showPlayers());
        auctionBtn.addActionListener(e -> showAuction());
        resultBtn.addActionListener(e -> showResults());

        left.add(teamsBtn);
        left.add(playersBtn);
        left.add(auctionBtn);
        left.add(resultBtn);

        f.add(left, BorderLayout.WEST);
        f.add(new JLabel("Welcome To Online Auction System", SwingConstants.CENTER),
                BorderLayout.CENTER);

        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    static void showTeams() {
        JOptionPane.showMessageDialog(null,
                String.join("\n", teams),
                "IPL Teams",
                JOptionPane.INFORMATION_MESSAGE);
    }

    static void showPlayers() {
        JOptionPane.showMessageDialog(null,
                String.join("\n", players),
                "Players",
                JOptionPane.INFORMATION_MESSAGE);
    }

    static void showAuction() {
        JFrame f = new JFrame("Auction");
        f.setSize(500,400);

        JComboBox<String> playerBox = new JComboBox<>(players);
        JComboBox<String> teamBox = new JComboBox<>(teams);
        JTextField bidField = new JTextField();

        JLabel highest = new JLabel("Highest Bid : ₹100000");
        JLabel timerLabel = new JLabel("Time Left : 60");

        final int[] highestBid = {100000};
        final String[] winner = {"None"};
        final int[] timeLeft = {60};

        Timer timer = new Timer(1000, e -> {
            timeLeft[0]--;
            timerLabel.setText("Time Left : " + timeLeft[0]);

            if(timeLeft[0] <= 0){
                ((Timer)e.getSource()).stop();
                JOptionPane.showMessageDialog(f,
                        "Winner Team : " + winner[0] +
                                "\nWinning Bid : ₹" + highestBid[0]);
            }
        });

        JButton bidBtn = new JButton("Place Bid");

        bidBtn.addActionListener(e -> {
            try{
                int bid = Integer.parseInt(bidField.getText());

                if(bid > highestBid[0]){
                    highestBid[0] = bid;
                    winner[0] = teamBox.getSelectedItem().toString();

                    highest.setText(
                            "Highest Bid : " + winner[0] + " - ₹" + bid);

                    bidHistory.add(new String[]{
                            playerBox.getSelectedItem().toString(),
                            winner[0],
                            String.valueOf(bid)
                    });

                }else{
                    JOptionPane.showMessageDialog(f,
                            "Bid must be greater than current highest bid.");
                }

            }catch(Exception ex){
                JOptionPane.showMessageDialog(f,
                        "Enter valid amount");
            }
        });

        JPanel p = new JPanel(new GridLayout(7,1,10,10));
        p.add(playerBox);
        p.add(teamBox);
        p.add(new JLabel("Base Price : ₹1,00,000"));
        p.add(bidField);
        p.add(highest);
        p.add(timerLabel);
        p.add(bidBtn);

        f.add(p);
        timer.start();

        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    static void showResults() {
        JFrame f = new JFrame("Results");
        f.setSize(500,400);

        JTextArea area = new JTextArea();

        if(bidHistory.isEmpty()){
            area.setText("No Results Available");
        } else {
            for(String[] b : bidHistory){
                area.append(b[0] + " -> " + b[1] + " -> ₹" + b[2] + "\n");
            }
        }

        f.add(new JScrollPane(area));
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
