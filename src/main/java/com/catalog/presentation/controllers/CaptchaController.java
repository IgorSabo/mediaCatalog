package com.catalog.presentation.controllers;

import com.github.cage.Cage;
import com.github.cage.GCage;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Gile on 10/23/2016.
 */

@Controller
public class CaptchaController {



    Cage cage = new Cage();
    static HttpHeaders headers = new HttpHeaders();


    public static void generateToken(HttpSession session) {
        /*final String token = cage.getTokenGenerator().next();

        session.setAttribute("captchaToken", token);
        markTokenUsed(session, false);*/
    }


    public static String getToken(HttpSession session) {
        final Object val = session.getAttribute("captchaToken");

        return val != null ? val.toString() : null;
    }

    protected static void markTokenUsed(HttpSession session, boolean used) {
        session.setAttribute("captchaTokenUsed", used);
    }

    protected static boolean isTokenUsed(HttpSession session) {
        return !Boolean.FALSE.equals(session.getAttribute("captchaTokenUsed"));
    }

    protected void setResponseHeaders(HttpHeaders headers) {
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setCacheControl("no-cache, no-store"); //setHeader("Cache-Control", "no-cache, no-store");
        headers.setPragma("no-cache"); //setHeader("Pragma", "no-cache");
        final long time = System.currentTimeMillis();
        headers.setLastModified(time); //setDateHeader("Last-Modified", time);
        headers.setDate("Date", time); //setDateHeader("Date", time);
        headers.setExpires(time); //setDateHeader("Expires", time);
    }


    @RequestMapping(value = "/getCaptcha", method = RequestMethod.GET)
    public String redirectToCaptchaPage(HttpServletRequest req)
    {
        Cage cage = new Cage();
        final String token = cage.getTokenGenerator().next();

        HttpSession session = req.getSession(true);
        session.setAttribute("captchaToken", token);
        return "captchaTest";
    }

    @RequestMapping(value = "/getCaptchaImage", method = RequestMethod.GET, produces = MediaType. IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImageFile(HttpServletRequest req, HttpServletResponse res)  {
        try {

            final HttpSession session = req.getSession(false);
            //generateToken(session);
            final String token = session != null ? getToken(session) : null;
            /*if (token == null || isTokenUsed(session)) {
                //resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Captcha not found.");
                //TODO throw exception
                //return;
            }*/


            //markTokenUsed(session, true);
            byte[] array = cage.draw(token);
            ByteArrayOutputStream bao = new ByteArrayOutputStream(array.length);
            bao.write(array);


            setResponseHeaders(headers);
            return new ResponseEntity<byte[]>(bao.toByteArray(), headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            //logger.error(e);
            throw new RuntimeException(e);
        }
    }
}
