package com.jackie.matchingtradingsystemserver.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

public class Captcha {
    /*
    验证码
    * */
    private String code;

    /*
    * 图片
    * */
    private BufferedImage buffImage;


    /*
    * 随机数发生器
    * */
    private Random random = new Random();

    public Captcha(int width, int height, int codeCount, int lineCount){

        //1.生成图像
        buffImage = new BufferedImage(width,height,
                BufferedImage.TYPE_INT_RGB);
        //2.背景色
        Graphics graphics = buffImage.getGraphics(); //拿到图像画笔
        graphics.setColor(getRandColor(200,250)); //画笔设置随机色，200和250表示颜色范围
        graphics.fillRect(0,0,width,height); //把随机色填充到背景
        Font font = new Font("Fixedsys",Font.BOLD,height - 5); //设置字体
        graphics.setFont(font);

        //3.生成干扰线 噪点
        //干扰线
        for (int i = 0; i < lineCount; i++) {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs + random.nextInt(width);
            int ye = ys + random.nextInt(height);
            graphics.setColor(getRandColor(1,255));
            graphics.drawLine(xs,ys,xe,ye);
        }
        //噪点
        float yawpRate = 0.01f;
        int area = (int) (yawpRate * width * height);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            buffImage.setRGB(x, y, random.nextInt(255));
        }
        //4.添加字符
        this.code = randomStr(codeCount);
        //5.把数字画到画布
        /* 让画的更加均匀 */
        int fontWidth = width/codeCount;
        int fontHeight = height -5;
        //画的逻辑
        for(int i=0; i< codeCount; i++){
            String str = this.code.substring(i,i+1);
            graphics.setColor(getRandColor(1,255));
            graphics.drawString(str,i*fontWidth + 3,fontHeight-3);
        }



    }

    private Color getRandColor(int fc, int bc){
        if(fc > 255){
            fc = 255;
        }
        if(bc > 255){
            bc = 255;
        }
        int r = fc + random.nextInt(bc-fc);
        int g = fc + random.nextInt(bc-fc);
        int b = fc + random.nextInt(bc-fc);
        return new Color(r,g,b);
    }

    /*
        随机生成字符
        codeCount：字符数量
     */
    private String randomStr(int codeCount){
        //生成的字符从这个字典挑选出来
        String magicStr = "ABCDEFGHJKMNOPQRSTUVWXYZabcdefghjkmnopqrstuvwxyz1234567890";
        StringBuilder sb = new StringBuilder();
        int len = magicStr.length() -1;
        double r;
        for (int i = 0; i < codeCount; i++) {
            r = (Math.random()) * len;
            sb.append(magicStr.charAt((int)r)); //随机数作为magicStr字典某一项索引
        }
        return sb.toString();
    }


    public String getCode(){
        return code.toLowerCase();
    }

    public String getBase64ByteStr() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(buffImage,"png",baos); //当前图片的字节流

        String s = Base64.getEncoder().encodeToString(baos.toByteArray()); //Base64对流进行编码
        //去掉回车和换行
        s = s.replaceAll("\n","")
                .replaceAll("\r","");

        return "data:image/jpg;base64," + s;

    }



}

