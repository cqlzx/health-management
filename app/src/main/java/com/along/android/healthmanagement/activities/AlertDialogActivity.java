package com.along.android.healthmanagement.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.EditText;
import android.widget.Toast;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.EmergencyContact;
import com.along.android.healthmanagement.entities.NotificationResponse;
import com.along.android.healthmanagement.entities.Prescription;
import com.along.android.healthmanagement.entities.User;
import com.along.android.healthmanagement.helpers.EntityManager;
import com.along.android.healthmanagement.helpers.MailHelper;

import java.util.Calendar;


public class AlertDialogActivity extends Activity {

    private static final String PRESCRIPTION_ID = "PRESCRIPTION_ID";
    private long uid, pid;
    private Ringtone r;
    private boolean isTaken = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("dialog", "!!!!!");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setNotificationResponse(false);
                r.stop();
                finish();
                if (!isTaken) {

                    Log.d("delay", "!!!!!");
                    sendContactEmail();
                }

            }
        }, 5 * 60000);


        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();

        SharedPreferences sp = getSharedPreferences("Login", Context.MODE_PRIVATE);
        uid = sp.getLong("uid", 0);
        pid = Integer.parseInt(getIntent().getStringExtra(PRESCRIPTION_ID));

        final Prescription prescription = EntityManager.findById(Prescription.class, pid);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        builder.setTitle("Health Management ")
                .setMessage("It's time to take the medicine : " + prescription.getMedication())
                .setCancelable(false)
                .setPositiveButton("Take them!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

//                        setNotificationResponse(true);

                        //This is only for the demo to show that we have stored the response of the user and the corresponding uid and pid
                        Toast.makeText(AlertDialogActivity.this, "Medicine is taken! Uid = " + uid + ", pid = " + pid, Toast.LENGTH_SHORT).show();
                        isTaken = true;
                        r.stop();
                        finish();
                        Log.d("confirm", "!!!!!");
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        setNotificationResponse(false);

                        //This is only for the demo to show that we have stored the response of the user and the corresponding uid and pid
                        Toast.makeText(AlertDialogActivity.this, "Medicine is NOT taken! Uid = " + uid + ", pid = " + pid, Toast.LENGTH_SHORT).show();

                        r.stop();
                        finish();
                        Log.d("cancel", "!!!!!");

                        sendContactEmail();
                        isTaken = true;
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public static Intent newIntent(Context context, String extra) {
        Intent i = new Intent(context, AlertDialogActivity.class);
        i.putExtra(PRESCRIPTION_ID, extra);
        return i;
    }

    private void setNotificationResponse(boolean response) {

        NotificationResponse nr = new NotificationResponse();

        nr.setUserId((long) uid);
        nr.setPrescriptionId((long) pid);
        nr.setNotificationResponse(response);
        nr.setTime(Calendar.getInstance().getTimeInMillis());

        nr.save();

    }

    private void sendContactEmail() {
        SharedPreferences sp = getSharedPreferences("Login", Context.MODE_PRIVATE);
        Long userIdS = sp.getLong("uid", 0);
        User user = EntityManager.findById(User.class, userIdS);
        String subject = "HealthManagement Support";

        EmergencyContact ec = EntityManager.findOneBy(EmergencyContact.class, "uid = ?", userIdS + "");
        if (null != ec) {
            String email = ec.getEmail();
            Log.d("email========", email);
            String content = makeEmailContentFromEmergencyContact(ec, user);
            new MailHelper().execute(email, subject, content);
        } else {
            String email = user.getEmail();
            String content = makeEmailContent(user);
            new MailHelper().execute(email, subject, content);
        }


    }

    private String makeEmailContent(User user) {
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> \n" +
                "<html>\n" +
                "<head>\n" +
                "\n" +
                "\t<!-- Define Charset -->\n" +
                "\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "\t\n" +
                "\t<!-- Responsive Meta Tag -->\n" +
                "\t<meta name=\"viewport\" content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0;\" />\n" +
                "\n" +
                "\t<link href='http://fonts.googleapis.com/css?family=Open Sans:400,300,500,700' rel='stylesheet' type='text/css'>\n" +
                "\t\n" +
                "\t\n" +
                "    <title>Faux - Responsive Email Template</title><!-- Responsive Styles and Valid Styles -->\n" +
                "\n" +
                "    <style type=\"text/css\">\n" +
                "    \t\n" +
                "    \t@font-face {\n" +
                "\t\t    font-family: 'lane_-_narrowregular';\n" +
                "\t\t    src: url('http://pickedmail.com/Faux/demo/fonts/lanenar-webfont.eot');\n" +
                "\t\t    src: url('http://pickedmail.com/Faux/demo/fonts/lanenar-webfont.eot?#iefix') format('embedded-opentype'),\n" +
                "\t\t         url('http://pickedmail.com/Faux/demo/fonts/lanenar-webfont.woff') format('woff'),\n" +
                "\t\t         url('http://pickedmail.com/Faux/demo/fonts/lanenar-webfont.ttf') format('truetype'),\n" +
                "\t\t         url('http://pickedmail.com/Faux/demo/fonts/lanenar-webfont.svg#lane_-_narrowregular') format('svg');\n" +
                "\t\t    font-weight: normal;\n" +
                "\t\t    font-style: normal;\n" +
                "\t\t\n" +
                "\t\t}\n" +
                "    \t\n" +
                "\t    body{\n" +
                "            width: 100%; \n" +
                "            background-color: #f3f3f3; \n" +
                "            margin:0; \n" +
                "            padding:0; \n" +
                "            -webkit-font-smoothing: antialiased;\n" +
                "            mso-margin-top-alt:0px; mso-margin-bottom-alt:0px; mso-padding-alt: 0px 0px 0px 0px;\n" +
                "        }\n" +
                "        \n" +
                "        p,h1,h2,h3,h4{\n" +
                "\t        margin-top:0;\n" +
                "\t\t\tmargin-bottom:0;\n" +
                "\t\t\tpadding-top:0;\n" +
                "\t\t\tpadding-bottom:0;\n" +
                "        }\n" +
                "        \n" +
                "        span.preheader{display: none; font-size: 1px;}\n" +
                "        \n" +
                "        html{\n" +
                "            width: 100%; \n" +
                "        }\n" +
                "        \n" +
                "        table{\n" +
                "            font-size: 14px;\n" +
                "            border: 0;\n" +
                "            transition: all .5s;\n" +
                "        }\n" +
                "\t\t\n" +
                "\t\ttable td{\n" +
                "\t\t\ttransition: all .5s;\n" +
                "\t\t}\n" +
                "\n" +
                "\n" +
                "\t\t #active-tg{\n" +
                "\t        list-style: none;\n" +
                "\t        margin: 0;\n" +
                "\t        padding: 0;\n" +
                "        }\n" +
                "        #active-tg li{\n" +
                "\t        position: relative;\n" +
                "\t        cursor: n-resize;\n" +
                "        }\n" +
                "               \n" +
                "\t\t.test{\n" +
                "\t\t\twidth: 100%;\n" +
                "\t\t\tposition: relative;\n" +
                "\t\t}\n" +
                "\t\t\n" +
                "\t\t.test .icon {\n" +
                "\t\t\tposition: absolute;\n" +
                "\t\t\ttop: 2px;\n" +
                "\t\t\tright: 2px;\n" +
                "\t\t}\n" +
                "\t\t\t\t\n" +
                "\t\t#active-tg .test .icon img{width: 35px !important; height: 27px !important;}\n" +
                "\t\t\n" +
                "\n" +
                "\t\t\t\t\n" +
                "\t\t /* ----------- responsivity ----------- */\n" +
                "        @media only screen and (max-width: 640px){\n" +
                "\t\t\t/*------ top header ------ */\t\n" +
                "            body[yahoo] .main-header{font-size: 36px !important;}\n" +
                "            body[yahoo] .main-section-header{font-size: 25px !important;}\n" +
                "            body[yahoo] .show{display: block !important;}\n" +
                "            body[yahoo] .hide{display: none !important;}\n" +
                "            \n" +
                "            /*----- main image -------*/\n" +
                "\t\t\tbody[yahoo] .main-image img{width: 440px !important; height: auto !important;}\n" +
                "\t\t\t \n" +
                "\t\t\t/* ====== divider ====== */\n" +
                "\t\t\tbody[yahoo] .divider img{width: 440px !important;}\n" +
                "\t\t\t\n" +
                "\t\t\t/*--------- banner ----------*/\n" +
                "\t\t\tbody[yahoo] .banner img{width: 440px !important; height: auto !important;}\n" +
                "\t\t\t/*-------- container --------*/\t\t\t\n" +
                "\t\t\tbody[yahoo] .container590{width: 440px !important;}\n" +
                "\t\t\tbody[yahoo] .container580{width: 400px !important;}\n" +
                "\t\t\tbody[yahoo] .half-container590{width: 200px !important;}\n" +
                "           \n" +
                "\t\t\t/*-------- secions ----------*/\n" +
                "\t\t\tbody[yahoo] .section-item{width: 440px !important;}\n" +
                "            body[yahoo] .section-img img{width: 440px !important; height: auto !important;}        \n" +
                "        }\n" +
                "\n" +
                "\t\t@media only screen and (max-width: 479px){\n" +
                "\t\t\t/*------ top header ------ */\n" +
                "\t\t\tbody[yahoo] .main-header{font-size: 28px !important; line-height: 40px !important;}\n" +
                "            body[yahoo] .main-header div{line-height: 40px !important;}\n" +
                "            \n" +
                "            /*----- main image -------*/\n" +
                "\t\t\tbody[yahoo] .main-image img{width: 280px !important; height: auto !important;}\n" +
                "\t\t\t \n" +
                "\t\t\t/* ====== divider ====== */\n" +
                "\t\t\tbody[yahoo] .divider{width: 280px !important;}\n" +
                "\t\t\tbody[yahoo] .align-center{text-align: center !important;}\n" +
                "\t\t\t\n" +
                "\t\t\t/*--------- banner ----------*/\n" +
                "\t\t\tbody[yahoo] .banner img{width: 280px !important; height: auto !important;}\n" +
                "\t\t\t/*-------- container --------*/\t\t\t\n" +
                "\t\t\tbody[yahoo] .container590{width: 280px !important;}\n" +
                "\t\t\tbody[yahoo] .container580{width: 260px !important;}\n" +
                "\t\t\tbody[yahoo] .half-section{width: 200px !important;}\n" +
                "           \n" +
                "\t\t\t/*-------- secions ----------*/\n" +
                "\t\t\tbody[yahoo] .section-item{width: 280px !important;}\n" +
                "\t\t\tbody[yahoo] .section-item-iphone{width: 280px !important;}\n" +
                "            body[yahoo] .section-img img{width: 280px !important; height: auto !important;}  \n" +
                "            body[yahoo] .section-iphone-img img{width: 280px !important; height: auto !important;}\n" +
                "            \n" +
                "            /*------- CTA -------------*/\n" +
                "            \n" +
                "\t\t\tbody[yahoo] .cta-btn img{width: 260px !important; height: auto !important;}\n" +
                "\t\t}\n" +
                "\t\t\n" +
                "</style>\n" +
                "</head>\n" +
                "\n" +
                "<body yahoo=\"fix\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n" +
                "\t\n" +
                "\t<!-- ======= main section ======= -->\n" +
                "\t<ul id=\"active-tg\">\n" +
                "\n" +
                "\t<table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"000000\" mc:repeatable=\"active-tg\" mc:variant=\"main section with bg\" class=\"overlay_color\">\n" +
                "\t\t\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td style=\"background-size: 100% 100%; background-position: top center; background-repeat: repeat;\" class=\"main-bg\">\n" +
                "\t\t\t\n" +
                "\t\t\t\t<table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590\">\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t<tr><td height=\"65\" style=\"font-size: 65px; line-height: 65px;\">&nbsp;</td></tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t\t<table border=\"0\" width=\"500\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590\">\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td align=\"center\" height=\"42\" mc:edit=\"main-header\" style=\"color: #ffffff; font-size: 36px; font-family: Open Sans, sans-serif; mso-line-height-rule: exactly; line-height: 58px;\" class=\"main-header white_color\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t<!-- ======= section header ======= -->\n" +
                "\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t<div class=\"editable_text\" style=\"line-height: 58px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<span class=\"text_container\">\n" +
                "\t\t\t\t\t        \t\t\t\t\t<multiline>\n" +
                "\t\t\t\t\t        \t\t\t\t\t\tDear\n" + user.getRealname() + ":" +
                "\t\t\t\t\t        \t\t\t\t\t</multiline>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</span>\n" +
                "\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t        \t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t<tr><td height=\"85\" style=\"font-size: 85px; line-height: 85px;\">&nbsp;</td></tr>\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t\t<table border=\"0\" width=\"500\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590\">\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td align=\"center\" height=\"42\" mc:edit=\"main-header\" style=\"color: #ffffff; font-size: 36px; font-family: Open Sans, sans-serif; mso-line-height-rule: exactly; line-height: 58px;\" class=\"main-header white_color\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t<!-- ======= section header ======= -->\n" +
                "\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t<div class=\"editable_text\" style=\"line-height: 58px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<span class=\"text_container\">\n" +
                "\t\t\t\t\t        \t\t\t\t\t<multiline>\n" +
                "\t\t\t\t\t        \t\t\t\t\t\t" + user.getRealname() + " didn`t take the medicine\n" +
                "\t\t\t\t\t        \t\t\t\t\t</multiline>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</span>\n" +
                "\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t        \t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t<tr><td height=\"35\" style=\"font-size: 35px; line-height: 35px;\">&nbsp;</td></tr>\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t\t<table border=\"0\" width=\"540\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590\">\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t<td align=\"center\" mc:edit=\"main-text\" style=\"color: #ffffff; font-size: 16px; font-family: 'Open Sans', Open Sans, sans-serif; mso-line-height-rule: exactly; line-height: 30px;\" class=\"white_color\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<div class=\"editable_text\" style=\"line-height: 30px\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<!-- ======= section text ======= -->\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<span class=\"text_container\">\n" +
                "\t\t\t\t\t        \t\t\t\t\t<multiline>\n" +
                "\t\t\t\t\t        \t\t\t\t\t\ttell " + (user.getGender().equals("Male") ? "him" : "her") + " \n<a href=\"tel:" + user.getPhonenumber() + "\" >" + user.getPhonenumber() + "</a>\n" +
                "\t\t\t\t\t        \t\t\t\t\t</multiline>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</span>\n" +
                "\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t        \t\t\t\t</td>\t\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t<tr><td height=\"40\" style=\"font-size: 40px; line-height: 40px;\">&nbsp;</td></tr>\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t<table border=\"0\" align=\"center\" width=\"176\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"d91a23\" style=\"border-radius: 5px;\" class=\"main_color\">\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t<tr><td height=\"15\" style=\"font-size: 15px; line-height: 15px;\">&nbsp;</td></tr>\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t<td>\n" +
                "\t\t\t\t\t\t\t\t\t\t<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t                    \t\t\t\t\t\n" +
                "\t\t\t\t                \t\t\t\t<td align=\"right\" style=\"color: #ffffff; font-size: 14px; font-family:'Open Sans', sans-serif;\" mc:edit=\"main-button\">\n" +
                "\t\t\t\t                \t\t\t\t\t<!-- ======= main section button ======= -->\n" +
                "\t\t\t\t                \t\t\t\t\t\n" +
                "\t\t\t\t\t                    \t\t\t<div class=\"editable_text\" style=\"line-height: 24px;\">\n" +
                "\t\t\t\t\t                    \t\t\t\t<span class=\"text_container\">\n" +
                "\t\t\t\t\t\t                    \t\t\t<a href=\"https://github.com/cqlzx/HealthManagement\" style=\"color: #ffffff; text-decoration: none;\"><singleline>Get Our App</singleline></a> \n" +
                "\t\t\t\t\t                    \t\t\t\t</span>\n" +
                "\t\t\t\t\t                    \t\t\t</div>\n" +
                "\t\t\t\t\t                    \t\t</td>\n" +
                "\t\t\t\t\t                    \t\t\n" +
                "\t\t\t\t                \t\t\t</tr>\n" +
                "\t\t\t\t                \t\t\t\n" +
                "\t\t\t                \t\t\t</table>\n" +
                "\t\t\t                \t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t<tr><td height=\"15\" style=\"font-size: 15px; line-height: 15px;\">&nbsp;</td></tr>\n" +
                "\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t<tr><td height=\"130\" style=\"font-size: 135px; line-height: 130px;\">&nbsp;</td></tr>\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t</td>\n" +
                "\t\t</tr>\n" +
                "\t\t\n" +
                "\t</table>\n" +
                "\t<!-- ======= end main section ======= -->\n" +
                "\n" +
                "\n" +
                "\t\n" +
                "</body>\n" +
                "</html>"
        ;

    }

    private String makeEmailContentFromEmergencyContact(EmergencyContact ec, User user) {
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> \n" +
                "<html>\n" +
                "<head>\n" +
                "\n" +
                "\t<!-- Define Charset -->\n" +
                "\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "\t\n" +
                "\t<!-- Responsive Meta Tag -->\n" +
                "\t<meta name=\"viewport\" content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0;\" />\n" +
                "\n" +
                "\t<link href='http://fonts.googleapis.com/css?family=Open Sans:400,300,500,700' rel='stylesheet' type='text/css'>\n" +
                "\t\n" +
                "\t\n" +
                "    <title>Faux - Responsive Email Template</title><!-- Responsive Styles and Valid Styles -->\n" +
                "\n" +
                "    <style type=\"text/css\">\n" +
                "    \t\n" +
                "    \t@font-face {\n" +
                "\t\t    font-family: 'lane_-_narrowregular';\n" +
                "\t\t    src: url('http://pickedmail.com/Faux/demo/fonts/lanenar-webfont.eot');\n" +
                "\t\t    src: url('http://pickedmail.com/Faux/demo/fonts/lanenar-webfont.eot?#iefix') format('embedded-opentype'),\n" +
                "\t\t         url('http://pickedmail.com/Faux/demo/fonts/lanenar-webfont.woff') format('woff'),\n" +
                "\t\t         url('http://pickedmail.com/Faux/demo/fonts/lanenar-webfont.ttf') format('truetype'),\n" +
                "\t\t         url('http://pickedmail.com/Faux/demo/fonts/lanenar-webfont.svg#lane_-_narrowregular') format('svg');\n" +
                "\t\t    font-weight: normal;\n" +
                "\t\t    font-style: normal;\n" +
                "\t\t\n" +
                "\t\t}\n" +
                "    \t\n" +
                "\t    body{\n" +
                "            width: 100%; \n" +
                "            background-color: #f3f3f3; \n" +
                "            margin:0; \n" +
                "            padding:0; \n" +
                "            -webkit-font-smoothing: antialiased;\n" +
                "            mso-margin-top-alt:0px; mso-margin-bottom-alt:0px; mso-padding-alt: 0px 0px 0px 0px;\n" +
                "        }\n" +
                "        \n" +
                "        p,h1,h2,h3,h4{\n" +
                "\t        margin-top:0;\n" +
                "\t\t\tmargin-bottom:0;\n" +
                "\t\t\tpadding-top:0;\n" +
                "\t\t\tpadding-bottom:0;\n" +
                "        }\n" +
                "        \n" +
                "        span.preheader{display: none; font-size: 1px;}\n" +
                "        \n" +
                "        html{\n" +
                "            width: 100%; \n" +
                "        }\n" +
                "        \n" +
                "        table{\n" +
                "            font-size: 14px;\n" +
                "            border: 0;\n" +
                "            transition: all .5s;\n" +
                "        }\n" +
                "\t\t\n" +
                "\t\ttable td{\n" +
                "\t\t\ttransition: all .5s;\n" +
                "\t\t}\n" +
                "\n" +
                "\n" +
                "\t\t #active-tg{\n" +
                "\t        list-style: none;\n" +
                "\t        margin: 0;\n" +
                "\t        padding: 0;\n" +
                "        }\n" +
                "        #active-tg li{\n" +
                "\t        position: relative;\n" +
                "\t        cursor: n-resize;\n" +
                "        }\n" +
                "               \n" +
                "\t\t.test{\n" +
                "\t\t\twidth: 100%;\n" +
                "\t\t\tposition: relative;\n" +
                "\t\t}\n" +
                "\t\t\n" +
                "\t\t.test .icon {\n" +
                "\t\t\tposition: absolute;\n" +
                "\t\t\ttop: 2px;\n" +
                "\t\t\tright: 2px;\n" +
                "\t\t}\n" +
                "\t\t\t\t\n" +
                "\t\t#active-tg .test .icon img{width: 35px !important; height: 27px !important;}\n" +
                "\t\t\n" +
                "\n" +
                "\t\t\t\t\n" +
                "\t\t /* ----------- responsivity ----------- */\n" +
                "        @media only screen and (max-width: 640px){\n" +
                "\t\t\t/*------ top header ------ */\t\n" +
                "            body[yahoo] .main-header{font-size: 36px !important;}\n" +
                "            body[yahoo] .main-section-header{font-size: 25px !important;}\n" +
                "            body[yahoo] .show{display: block !important;}\n" +
                "            body[yahoo] .hide{display: none !important;}\n" +
                "            \n" +
                "            /*----- main image -------*/\n" +
                "\t\t\tbody[yahoo] .main-image img{width: 440px !important; height: auto !important;}\n" +
                "\t\t\t \n" +
                "\t\t\t/* ====== divider ====== */\n" +
                "\t\t\tbody[yahoo] .divider img{width: 440px !important;}\n" +
                "\t\t\t\n" +
                "\t\t\t/*--------- banner ----------*/\n" +
                "\t\t\tbody[yahoo] .banner img{width: 440px !important; height: auto !important;}\n" +
                "\t\t\t/*-------- container --------*/\t\t\t\n" +
                "\t\t\tbody[yahoo] .container590{width: 440px !important;}\n" +
                "\t\t\tbody[yahoo] .container580{width: 400px !important;}\n" +
                "\t\t\tbody[yahoo] .half-container590{width: 200px !important;}\n" +
                "           \n" +
                "\t\t\t/*-------- secions ----------*/\n" +
                "\t\t\tbody[yahoo] .section-item{width: 440px !important;}\n" +
                "            body[yahoo] .section-img img{width: 440px !important; height: auto !important;}        \n" +
                "        }\n" +
                "\n" +
                "\t\t@media only screen and (max-width: 479px){\n" +
                "\t\t\t/*------ top header ------ */\n" +
                "\t\t\tbody[yahoo] .main-header{font-size: 28px !important; line-height: 40px !important;}\n" +
                "            body[yahoo] .main-header div{line-height: 40px !important;}\n" +
                "            \n" +
                "            /*----- main image -------*/\n" +
                "\t\t\tbody[yahoo] .main-image img{width: 280px !important; height: auto !important;}\n" +
                "\t\t\t \n" +
                "\t\t\t/* ====== divider ====== */\n" +
                "\t\t\tbody[yahoo] .divider{width: 280px !important;}\n" +
                "\t\t\tbody[yahoo] .align-center{text-align: center !important;}\n" +
                "\t\t\t\n" +
                "\t\t\t/*--------- banner ----------*/\n" +
                "\t\t\tbody[yahoo] .banner img{width: 280px !important; height: auto !important;}\n" +
                "\t\t\t/*-------- container --------*/\t\t\t\n" +
                "\t\t\tbody[yahoo] .container590{width: 280px !important;}\n" +
                "\t\t\tbody[yahoo] .container580{width: 260px !important;}\n" +
                "\t\t\tbody[yahoo] .half-section{width: 200px !important;}\n" +
                "           \n" +
                "\t\t\t/*-------- secions ----------*/\n" +
                "\t\t\tbody[yahoo] .section-item{width: 280px !important;}\n" +
                "\t\t\tbody[yahoo] .section-item-iphone{width: 280px !important;}\n" +
                "            body[yahoo] .section-img img{width: 280px !important; height: auto !important;}  \n" +
                "            body[yahoo] .section-iphone-img img{width: 280px !important; height: auto !important;}\n" +
                "            \n" +
                "            /*------- CTA -------------*/\n" +
                "            \n" +
                "\t\t\tbody[yahoo] .cta-btn img{width: 260px !important; height: auto !important;}\n" +
                "\t\t}\n" +
                "\t\t\n" +
                "</style>\n" +
                "</head>\n" +
                "\n" +
                "<body yahoo=\"fix\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n" +
                "\t\n" +
                "\t<!-- ======= main section ======= -->\n" +
                "\t<ul id=\"active-tg\">\n" +
                "\n" +
                "\t<table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"000000\" mc:repeatable=\"active-tg\" mc:variant=\"main section with bg\" class=\"overlay_color\">\n" +
                "\t\t\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td style=\"background-size: 100% 100%; background-position: top center; background-repeat: repeat;\" class=\"main-bg\">\n" +
                "\t\t\t\n" +
                "\t\t\t\t<table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590\">\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t<tr><td height=\"65\" style=\"font-size: 65px; line-height: 65px;\">&nbsp;</td></tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t\t<table border=\"0\" width=\"500\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590\">\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td align=\"center\" height=\"42\" mc:edit=\"main-header\" style=\"color: #ffffff; font-size: 36px; font-family: Open Sans, sans-serif; mso-line-height-rule: exactly; line-height: 58px;\" class=\"main-header white_color\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t<!-- ======= section header ======= -->\n" +
                "\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t<div class=\"editable_text\" style=\"line-height: 58px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<span class=\"text_container\">\n" +
                "\t\t\t\t\t        \t\t\t\t\t<multiline>\n" +
                "\t\t\t\t\t        \t\t\t\t\t\tDear\n" + ec.getName() + ":" +
                "\t\t\t\t\t        \t\t\t\t\t</multiline>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</span>\n" +
                "\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t        \t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t<tr><td height=\"85\" style=\"font-size: 85px; line-height: 85px;\">&nbsp;</td></tr>\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t\t<table border=\"0\" width=\"500\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590\">\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td align=\"center\" height=\"42\" mc:edit=\"main-header\" style=\"color: #ffffff; font-size: 36px; font-family: Open Sans, sans-serif; mso-line-height-rule: exactly; line-height: 58px;\" class=\"main-header white_color\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t<!-- ======= section header ======= -->\n" +
                "\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t<div class=\"editable_text\" style=\"line-height: 58px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<span class=\"text_container\">\n" +
                "\t\t\t\t\t        \t\t\t\t\t<multiline>\n" +
                "\t\t\t\t\t        \t\t\t\t\t\t" + user.getRealname() + " didn`t take the medicine\n" +
                "\t\t\t\t\t        \t\t\t\t\t</multiline>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</span>\n" +
                "\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t        \t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t<tr><td height=\"35\" style=\"font-size: 35px; line-height: 35px;\">&nbsp;</td></tr>\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t\t<table border=\"0\" width=\"540\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590\">\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t<td align=\"center\" mc:edit=\"main-text\" style=\"color: #ffffff; font-size: 24px; font-family: 'Open Sans', Open Sans, sans-serif; mso-line-height-rule: exactly; line-height: 30px;\" class=\"white_color\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<div class=\"editable_text\" style=\"line-height: 30px\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<!-- ======= section text ======= -->\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<span class=\"text_container\">\n" +
                "\t\t\t\t\t        \t\t\t\t\t<multiline>\n" +
                "\t\t\t\t\t        \t\t\t\t\t\ttell " + (user.getGender().equals("Male") ? "him" : "her") + " \n<a href=\"tel:" + user.getPhonenumber() + "\" >" + user.getPhonenumber() + "</a>\n" +
                "\t\t\t\t\t        \t\t\t\t\t</multiline>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</span>\n" +
                "\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t        \t\t\t\t</td>\t\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t<tr><td height=\"40\" style=\"font-size: 40px; line-height: 40px;\">&nbsp;</td></tr>\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t<table border=\"0\" align=\"center\" width=\"176\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"d91a23\" style=\"border-radius: 5px;\" class=\"main_color\">\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t<tr><td height=\"15\" style=\"font-size: 15px; line-height: 15px;\">&nbsp;</td></tr>\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t<td>\n" +
                "\t\t\t\t\t\t\t\t\t\t<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t                    \t\t\t\t\t\n" +
                "\t\t\t\t                \t\t\t\t<td align=\"right\" style=\"color: #ffffff; font-size: 14px; font-family:'Open Sans', sans-serif;\" mc:edit=\"main-button\">\n" +
                "\t\t\t\t                \t\t\t\t\t<!-- ======= main section button ======= -->\n" +
                "\t\t\t\t                \t\t\t\t\t\n" +
                "\t\t\t\t\t                    \t\t\t<div class=\"editable_text\" style=\"line-height: 24px;\">\n" +
                "\t\t\t\t\t                    \t\t\t\t<span class=\"text_container\">\n" +
                "\t\t\t\t\t\t                    \t\t\t<a href=\"https://github.com/cqlzx/HealthManagement\" style=\"color: #ffffff; text-decoration: none;\"><singleline>Get Our App</singleline></a> \n" +
                "\t\t\t\t\t                    \t\t\t\t</span>\n" +
                "\t\t\t\t\t                    \t\t\t</div>\n" +
                "\t\t\t\t\t                    \t\t</td>\n" +
                "\t\t\t\t\t                    \t\t\n" +
                "\t\t\t\t                \t\t\t</tr>\n" +
                "\t\t\t\t                \t\t\t\n" +
                "\t\t\t                \t\t\t</table>\n" +
                "\t\t\t                \t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t<tr><td height=\"15\" style=\"font-size: 15px; line-height: 15px;\">&nbsp;</td></tr>\n" +
                "\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t<tr><td height=\"130\" style=\"font-size: 135px; line-height: 130px;\">&nbsp;</td></tr>\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t</td>\n" +
                "\t\t</tr>\n" +
                "\t\t\n" +
                "\t</table>\n" +
                "\t<!-- ======= end main section ======= -->\n" +
                "\n" +
                "\n" +
                "\t\n" +
                "</body>\n" +
                "</html>";
    }

}
