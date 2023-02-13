#Mail

```
mvn compile exec:java -Dexec.mainClass="com.example.service.mail.LocahostMail"
```


## Open Relay
```
mvn compile exec:java -Dexec.mainClass="com.example.service.mail.OpenRelayMail"
```

Here is the RAW email message with envelop and how the message traveled through...
```
Received: from mx07.pphosted.com([182.132.182.30])
        by mail.mailinator.com with SMTP (Mailinator)
        for email-receipient@mailinator.com;
        Sun, 12 Feb 2023 16:09:31 +0000 (UTC)
Received: from pps.filterd (m0246054.ppops.net [127.0.0.1])
	by receiver-hop1.pphosted.com (8.17.1.19/8.17.1.19) with ESMTP id 31DFipxF007645
	for <email-receipient@mailinator.com>; Mon, 12 Feb 2023 17:09:33 +0100
Received: from mshop-21.outbound.protection.outlook.com (mshop-2.outbound.protection.outlook.com [204.47.18.104])
	by receiver-hop1.pphosted.com (PPS) with ESMTPS id 3nqhx31dbf-1
	(version=TLSv1.2 cipher=ECDHE-RSA-AES256-GCM-SHA384 bits=256 verify=NOT)
	for <email-receipient@mailinator.com>; Mon, 12 Feb 2023 17:09:33 +0100
Received: from mshop1.eurprd05.prod.outlook.com (2603:10a6:10:36::42)
 by mshop2.eurprd03.prod.outlook.com (2603:10a6:10:19f::7) with
 Microsoft SMTP Server (version=TLS1_2,
 cipher=TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384) id 15.20.6086.24; Mon, 13 Feb
 2023 17:09:31 +0000
Received: from onehop.eop-EUR03.prod.protection.outlook.com
 (2603:10a6:10:36:cafe::a) by mshop1.outlook.office365.com
 (2603:10a6:10:36::42) with Microsoft SMTP Server (version=TLS1_2,
 cipher=TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384) id 15.20.6086.24 via Frontend
 Transport; Sun, 12 Feb 2023 16:09:31 +0000
Received: from ws003.example.com (194.26.68.53) by
 onehop.mail.protection.outlook.com (100.127.142.126) with Microsoft
 SMTP Server (version=TLS1_2, cipher=TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384) id
 15.20.6086.24 via Frontend Transport; Sun, 12 Feb 2023 16:09:31 +0000
Received: from sendhop2.internal (20.236.72.38) by
 mail.example.com (20.146.24.53) with Microsoft SMTP Server
 (version=TLS1_2, cipher=TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256_P256) id
 15.1.2507.16; Mon, 12 Feb 2023 17:05:33 +0100
Received: from sendhop1.internal (20.146.72.39) by
 sendhop2.internal (20.146.72.38) with Microsoft SMTP Server
 (version=TLS1_2, cipher=TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256) id
 15.1.2507.16; Mon, 12 Feb 2023 17:09:30 +0100
Received: from 172.20.20.20 (20.146.32.13) by sendhop1.internal
 (20.146.72.39) with Microsoft SMTP Server id 15.1.2507.16 via Frontend
 Transport; Mon, 12 Feb 2023 17:09:30 +0100
From: <your.email@example.com>
To: <email-receipient@mailinator.com>
Subject: This is the Subject Line!
Content-Type: text/plain; charset="us-ascii"
Content-Transfer-Encoding: quoted-printable
Date: Mon, 12 Feb 2023 17:09:30 +0100


This is actual message

NVOCC footer if configured



```

Here is some information to decipher above message
```
$ nslookup -q=mx mailinator.com
Server:		100.64.0.1
Address:	100.64.0.1#53

Non-authoritative answer:
mailinator.com	mail exchanger = 1 mail2.mailinator.com.
mailinator.com	mail exchanger = 1 mail.mailinator.com.

Authoritative answers can be found from:

```