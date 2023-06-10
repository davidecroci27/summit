# Summit
UNCOMPLETE university project - web development technologies

The website, implemented with Apache Tomcat and MySQL using java servlet on server side and JSP files on front-end side, mantains all the basic functionalities (log in of different types of users, creation of campaigns, tasks) in a nice yet simple interface, but lacks the more advanced functionalities (work statistics, comments, etc.) and documentation. This is because the version provided here was not the final one, that unfortunately went lost in an accident along with my old laptop, some months after i successfully passed the exam. Yes, i should have backed up the last version. I eventually found out that, well, i didn't.

I must admit I do not remember details about the set up of Tomcat or MySQL i used here, and i don't remember exactly which functionalities were already implemented in this version of the code, and which still weren't. In all honesty, I have no reason or motivation to investigate further, as this repository is just a showcase of a university homework, and the website doesn't have a real sense in the world, beside having been a training to comprehend how web applications are developed and how basic crowdsourcing functionalities work.

The description of how the website is suposed to work with full functionalities is defined below, translated from the document (in italian) provided by the professor to us students.

D.

------------------------------------------------------

Web Technologies

AY 2016 2017 


-- Project Specifications --


The project concerns the development of a web application for the management of crowdsourcing campaigns. A crowdsourcing campaign is created and published by an administrator and involves a group of workers. A user registers in the application with a role of her choice and logs in. An administrator can create one or more campaigns. Each campaign has a name, configuration parameters and input images.

A campaign consists of two tasks:

•	Image selection: the input data is an image; the output data is a Boolean value representing whether or not the image contains the outline of a mountain. The task must be performed by at least N users on the same image. The value of N is defined by the administrator for each campaign as a campaign configuration parameter.

•	Image annotation: the input data is an image; the output data is a line (or a set of lines, if the skyline in the image is interrupted by obstacles) which identifies the profile (skyline) of the mountain. This task must be performed by at least M users on the same image. The images input to the Image Annotation task are those that have received at least K positive evaluations in the Image Selection task. The values of M and K are defined by the administrator as configuration parameters for each campaign.

The delete button CANCELLA cancels the annotation in progress and allows you to start over. The button CONFERMA sends the annotation to the server. It should not be possible to send an unannotated image to the server. The size of the annotation line can be defined as a parameter of the campaign (positive integer number of pixels, from 1 to 10).

The administrator can perform the following tasks:
•	Register with their own personal data in the application.

•	Log in.

•	Create a campaign.

•	Configure the Image Select task associated with a campaign.

    o Define the minimum number N of users who must perform the task for each image

    o Define the K number of positive ratings an image must receive in the Image Selection task in order to be used in the Image Annotation task

•	Configure the Image Annotation task associated with the campaign.

    o Define the minimum number M of users who must perform the task for each image

    o Define the pixel dimensions of the annotation line.

•	Upload the images that the campaign needs to work on.

•	Enable a group of workers to perform the Image Selection task and a group to perform the Image Annotation task.

•	Start the campaign, which enters "non-editable" mode.

•	View approved and rejected images in the Image Selection task, with number of upvotes and downvotes.

•	View the annotations provided by workers for the images of the Image Annotation task.

•	View campaign statistics: Number of images approved and rejected, number of annotated images, number of annotations per image, average number of annotations per image for the campaign.

The worker can perform the following tasks:

•	Register with their own personal data in the application.

•	Log in.

•	View the list of campaigns and tasks for which they are enabled.

•	Perform a number of Image Selection and/or Annotation tasks.

•	View your work statistics for each campaign: Number of images approved and rejected, number of images annotated, number of images still to be approved, number of images still to be annotated.


Note:

•	The association phase between image, task and worker must guarantee the minimum number of workers for each image, but at the same time ensure the greatest possible coverage of the campaign images. The project documentation must accurately describe the policy used to assign images to workers, the reasons why this policy is effective, and any restrictions.
