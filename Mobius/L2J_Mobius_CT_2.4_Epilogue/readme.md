**VDS Vote Donate System**

Implementation of vote global and individual system with itopz donate panel task manager.

**Requirement**
- Mobius CT 2.4 Epilogue
- Patch installation (provided)
- JDK 14

**Build**
- File ```VDSystem.jar```
- Version: 1.0

**Global vote system for iTopZ**

- IP restricted rewards
- Reward every XX minutes
- Reward step every XX votes
- Reward item list will check if item exist
- Reward item list with chances 0-100%
- Reward item randomized of min-max values
- Update console votes and ranking
- Save votes in case of restarting the server
- Monthly reset for votes
- Print response failure message
- Configured announcement print of server vote statistics

**Individual vote system for iTopZ**

- Automatically register .itopz command
- Reward item list will check if item exist
- Reward item list with chances 0-100%
- Reward item randomized of min-max values
- Reward will set as "expired" after 12hours checking server time
- Reward reuse 12hours
- Command used after reward will show remaining time (PM/AM) to vote again
- Check for local ips is restriction
- Check for response errors
- Check if player voted

**Donate Management** (upgrade version of [Donate Panel](https://github.com/nightw0lv/DonatePanel))

- Player donates through Donate Panel
- Item will be rewarded in game

**Configs**

- Fully configured Console
- Fully configured global reward
- Fully configured individual reward

**Console**

- Information buttons
- Can send global reward in-game manually
- Prints rewards
- Prints donates
- Prints monthly reset
- Prints database updates
- Shows server ranking statistics
- Shows if you use DEMO or API key as mode status
- Auto scrolling messages
- Fully configured


**Note**

- Can be extended to add all topsites
- Can be used with LIVE and TEST modes

**Installation**
![https://github.com/nightw0lv/iTopZ-Java/tree/master/Mobius/L2J_Mobius_CT_2.4_Epilogue/VDSystem/Patch.diff](Patch.diff)


**TODO**

- Replace JSON result parsing with StringJoiner

```Special thanks to Rationale``` :cry:

**Images**
![https://prnt.sc/w6zkpg](../images/1.png)
![https://prnt.sc/w6zl79](../images/2.png)
![https://prnt.sc/w6zp4l](../images/3.png)
![https://prnt.sc/w6zqok](../images/4.png)
![https://prnt.sc/w6zuyx](../images/5.png)
![https://prnt.sc/w6zxyo](../images/6.png)
![https://prnt.sc/w6zzcs](../images/7.png)

**Chancelog**

- Version 1.0
  - Initial commit






 