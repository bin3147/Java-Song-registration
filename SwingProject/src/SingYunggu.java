import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

public class SingYunggu extends JFrame {
    static final String FILE_PATH = "D:\\singyou.txt";
    static ArrayList<Sing> songs = new ArrayList<>();
    static int idCounter = 1;

    private JPanel contentPane;
    private static JTextField titleField;
    private static JTextField singerField;
    private static JTextField genreField;
    private static JTextField lyricsField;
    private static JTextArea outputArea;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SingYunggu frame = new SingYunggu();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public SingYunggu() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("�뷡 ��� ���α׷�");
        setSize(400, 400);
        getContentPane().setLayout(new BorderLayout());

        contentPane = new JPanel(new GridLayout(4, 2));
        contentPane.add(new JLabel("�뷡 ����:"));
        titleField = new JTextField();
        contentPane.add(titleField);

        contentPane.add(new JLabel("�̸�:"));
        singerField = new JTextField();
        contentPane.add(singerField);

        contentPane.add(new JLabel("�帣:"));
        genreField = new JTextField();
        contentPane.add(genreField);

        contentPane.add(new JLabel("����:"));
        lyricsField = new JTextField();
        contentPane.add(lyricsField);

        JButton add = new JButton("�뷡���");
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerSong();
            }
        });
        contentPane.add(add);

        JButton list = new JButton("�뷡���");
        list.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displaySongs();
            }
        });
        contentPane.add(list);

        JButton cor = new JButton("�뷡����");
        cor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateSong();
            }
        });
        contentPane.add(cor);

        JButton del = new JButton("�뷡����");
        del.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSong();
            }
        });
        contentPane.add(del);

        JButton exit = new JButton("����");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        contentPane.add(exit);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane sPane = new JScrollPane(outputArea);
        getContentPane().add(contentPane, BorderLayout.NORTH);
        getContentPane().add(sPane, BorderLayout.CENTER);
    }

    public static void registerSong() {
        String title = titleField.getText();
        String singer = singerField.getText();
        String genre = genreField.getText();
        String lyrics = lyricsField.getText();

        Sing newSong = new Sing(idCounter++, title, singer, genre, lyrics);
        songs.add(newSong);
        saveToFile();

        outputArea.setText("�뷡 ��� �Ϸ�!");
    }

    public static void displaySongs() {
        outputArea.setText("");
        for (Sing song : songs) {
            outputArea.append(song.toString() + "\n");
        }
    }

    public static void saveToFile() {
        try (BufferedWriter wr = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Sing song : songs) {
                wr.write(song.getId() + ", " + song.getTitle() + ", " + song.getSinger() + ", " + song.getGenre() + ", " + song.getLyrics());
                wr.newLine();
            }
        } catch (IOException e) {
            outputArea.setText("���� ���� ����: " + e.getMessage());
        }
    }

    public static void updateSong() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("������ �뷡�� ���̵� �Է����ּ���: "));

            Sing songToUpdate = null;
            for (Sing sing : songs) {
                if (sing.getId() == id) {
                    songToUpdate = sing;
                    break;
                }
            }

            if (songToUpdate == null) {
                outputArea.setText("�ش� ���̵��� �뷡�� ã�� �� �����ϴ�.");
                return;
            }

            String newTitle = JOptionPane.showInputDialog("�� ������ �Է����ּ��� (����: " + songToUpdate.getTitle() + "): ");
            String newSinger = JOptionPane.showInputDialog("�� ������ �Է����ּ��� (����: " + songToUpdate.getSinger() + "): ");
            String newGenre = JOptionPane.showInputDialog("�� �帣�� �Է����ּ��� (����: " + songToUpdate.getGenre() + "): ");
            String newLyrics = JOptionPane.showInputDialog("�� ���縦 �Է����ּ��� (����: " + songToUpdate.getLyrics() + "): ");

            songToUpdate.setTitle(newTitle);
            songToUpdate.setSinger(newSinger);
            songToUpdate.setGenre(newGenre);
            songToUpdate.setLyrics(newLyrics);

            outputArea.setText("�뷡 ���� �Ϸ�!");
        } catch (NumberFormatException e) {
            outputArea.setText("�߸��� �Է��Դϴ�. ���ڸ� �Է����ּ���.");
        }
    }

    public static void deleteSong() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("������ �뷡�� ���̵� �Է����ּ���: "));

            Sing songToDelete = null;
            for (Sing song : songs) {
                if (song.getId() == id) {
                    songToDelete = song;
                    songs.remove(song);
                    saveToFile();
                    outputArea.setText("�뷡 ���� �Ϸ�!");
                    return;
                }
            }

            if (songToDelete == null) {
                outputArea.setText("�ش� ���̵��� �뷡�� ã�� �� �����ϴ�.");
            }
        } catch (NumberFormatException e) {
            outputArea.setText("�߸��� �Է��Դϴ�. ���ڸ� �Է����ּ���.");
        }
    }
}

class Sing {
    int id;
    String title;
    String singer;
    String genre;
    String lyrics;

    public Sing(int id, String title, String singer, String genre, String lyrics) {
        this.id = id;
        this.title = title;
        this.singer = singer;
        this.genre = genre;
        this.lyrics = lyrics;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

  public String getSinger() {
	  return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String toString() {
    	 return "���̵�: " + id + ", ����: " + title + ", ����: " + singer + ", �帣: " + genre + ", ����: " + lyrics; }
    }

