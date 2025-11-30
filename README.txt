                       !   הוראות  !
------------------------------------------------------

דרישות מערכת:
--------------

- סביבת פיתוח (IDE) כמו IntelliJ IDEA, Eclipse, או NetBeans
- או שימוש ב-Command Line



הרצה באמצעות Command Line:
-----------------------------
1. פתחו Command Prompt או Terminal
2. נווטו לתיקיית הפרויקט
3. היכנסו לתיקיית src/main/java
4. הרצו את הפקודות הבאות:

   Windows:
   javac com/migdal/todo/*.java
   java com.migdal.todo.Main

   Linux/Mac:
   javac com/migdal/todo/*.java
   java com.migdal.todo.Main



הרצה באמצעות IDE:
-------------------
1. פתחו את הפרויקט ב-IDE שלכם
2. ודאו שהתיקייה src/main/java מוגדרת כסורק קוד (Source Root)
3. מצאו את הקובץ Main.java
4. לחצו על Run או F5



הערה:
--------------
- קובץ ה-JSON (tasks.json) יווצר אוטומטית בתיקיית הפרויקט בעת הרצה ראשונה
- כל הנתונים נשמרים בקובץ tasks.json
- ניתן למחוק את הקובץ tasks.json כדי להתחיל מחדש



תפריט האפליקציה:
-----------------
1. Add new task - הוספת משימה חדשה
2. List all tasks - הצגת כל המשימות
3. Update task - עדכון משימה קיימת
4. Delete task - מחיקת משימה
5. Mark task as DONE - סימון משימה כ-DONE
6. Search tasks - חיפוש משימות לפי טקסט
7. List tasks sorted by status - הצגת משימות ממוינות לפי סטטוס
8. Get task by ID - קבלת משימה לפי מזהה
9. Exit - יציאה מהאפליקציה
