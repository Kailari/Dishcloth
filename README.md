# Dishcloth
Repository name didn't support 'ä' so, instead of finnish word "Tiskirätti", I had to use it's english counterpart "Dishcloth".

# Installing 3rd party libraries
I'm really paranoid about licensing and copyright issues, so I didn't include lwjgl3 libraries within the repository. Instead, they need to be downloaded separately.

1. Download LWJGL3 from: http://www.lwjgl.org
2. Under the project folder, create folder called 'libs'
3. Copy lwjgl.jar from downloaded .zip to newly created libs/
4. Create folder "natives" under libs/, so that you end up with <PROJECT PATH>/libs/natives/
5. copy contents of folder "native" from .zip to folder you created in step 4.
6. Add "-Djava.library.path=libs/natives/" (without quotes) to VM arguments.
