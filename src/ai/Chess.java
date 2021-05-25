package ai;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Chess {
    private String Team;
    private Image image;
    private int x;
    private int y;

    public Chess() {
        Team = "";
        image = null;
        int x = 0 ;
        int y = 0;
    }

    public Chess(String team, String url, int x , int y) {
        image = resizeImage(url);
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image,60+(getY()-1)*150-20,60+(getX()-1)*150-20,null);
    }

    public String getTeam() {
        return Team;
    }

    public void setTeam(String team) {
        Team = team;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public Image resizeImage(String image) {
        BufferedImage img=null;
        try {
            img= ImageIO.read(new File(image));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Image dimg=img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        return dimg;
    }

    public Chess(String team, Image image, int x, int y) {
		super();
		Team = team;
		this.image = image;
		this.x = x;
		this.y = y;
	}

	public boolean inRange(int x , int y) {
        if ((60+(getY()-1)*150-x)*(60+(getY()-1)*150-x) + (60+(getX()-1)*150-y)*(60+(getX()-1)*150-y) <= 20*20) return true;
        return false;
    }

}
