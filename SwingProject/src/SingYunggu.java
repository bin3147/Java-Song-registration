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
        setTitle("노래 등록 프로그램");
        setSize(400, 400);
        getContentPane().setLayout(new BorderLayout());

        contentPane = new JPanel(new GridLayout(4, 2));
        contentPane.add(new JLabel("노래 제목:"));
        titleField = new JTextField();
        contentPane.add(titleField);

        contentPane.add(new JLabel("이름:"));
        singerField = new JTextField();
        contentPane.add(singerField);

        contentPane.add(new JLabel("장르:"));
        genreField = new JTextField();
        contentPane.add(genreField);

        contentPane.add(new JLabel("가사:"));
        lyricsField = new JTextField();
        contentPane.add(lyricsField);

        JButton add = new JButton("노래등록");
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerSong();
            }
        });
        contentPane.add(add);

        JButton list = new JButton("노래목록");
        list.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displaySongs();
            }
        });
        contentPane.add(list);

        JButton cor = new JButton("노래수정");
        cor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateSong();
            }
        });
        contentPane.add(cor);

        JButton del = new JButton("노래삭제");
        del.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSong();
            }
        });
        contentPane.add(del);

        JButton exit = new JButton("종료");
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

        outputArea.setText("노래 등록 완료!");
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
            outputArea.setText("파일 쓰기 오류: " + e.getMessage());
        }
    }

    public static void updateSong() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("수정할 노래의 아이디를 입력해주세요: "));

            Sing songToUpdate = null;
            for (Sing sing : songs) {
                if (sing.getId() == id) {
                    songToUpdate = sing;
                    break;
                }
            }

            if (songToUpdate == null) {
                outputArea.setText("해당 아이디의 노래를 찾을 수 없습니다.");
                return;
            }

            String newTitle = JOptionPane.showInputDialog("새 제목을 입력해주세요 (현재: " + songToUpdate.getTitle() + "): ");
            String newSinger = JOptionPane.showInputDialog("새 가수를 입력해주세요 (현재: " + songToUpdate.getSinger() + "): ");
            String newGenre = JOptionPane.showInputDialog("새 장르를 입력해주세요 (현재: " + songToUpdate.getGenre() + "): ");
            String newLyrics = JOptionPane.showInputDialog("새 가사를 입력해주세요 (현재: " + songToUpdate.getLyrics() + "): ");

            songToUpdate.setTitle(newTitle);
            songToUpdate.setSinger(newSinger);
            songToUpdate.setGenre(newGenre);
            songToUpdate.setLyrics(newLyrics);

            outputArea.setText("노래 수정 완료!");
        } catch (NumberFormatException e) {
            outputArea.setText("잘못된 입력입니다. 숫자를 입력해주세요.");
        }
    }

    public static void deleteSong() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("삭제할 노래의 아이디를 입력해주세요: "));

            Sing songToDelete = null;
            for (Sing song : songs) {
                if (song.getId() == id) {
                    songToDelete = song;
                    songs.remove(song);
                    saveToFile();
                    outputArea.setText("노래 삭제 완료!");
                    return;
                }
            }

            if (songToDelete == null) {
                outputArea.setText("해당 아이디의 노래를 찾을 수 없습니다.");
            }
        } catch (NumberFormatException e) {
            outputArea.setText("잘못된 입력입니다. 숫자를 입력해주세요.");
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
    	 return "아이디: " + id + ", 제목: " + title + ", 가수: " + singer + ", 장르: " + genre + ", 가사: " + lyrics; }
    }

