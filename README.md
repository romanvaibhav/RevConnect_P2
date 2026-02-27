# RevConnect — Full-Stack Monolithic Social Media Web Application

RevConnect is a full-stack monolithic social media web application that provides a platform for **personal users**, **businesses**, and **content creators** to connect and interact. Users can create posts, engage with content through likes and comments, build their network through connections and follows, and manage their profiles through an intuitive web interface.

The application features **responsive design**, **real-time notifications**, **personalized feeds**, and **role-based access control** for different user types.

---

## ✨ Key Features

### Personal User
#### Authentication & Profile
1. Register and create an account with email, username, and password  
2. Login using email/username and password  
3. Create and edit profile (name, bio/about, profile picture, location, optional website link)  
4. View my profile and other users’ profiles  
5. Search users by name or username  
6. Set profile privacy (public/private)

#### Post Management
7. Create text posts with optional hashtags  
8. View my posts on my profile  
9. View personalized feed with posts from connections and followed accounts  
10. Edit my posts  
11. Delete my posts

#### Social Interactions
12. Like posts (own and others’)  
13. Unlike posts  
14. Comment on posts  
15. View all comments on a post  
16. Delete my own comments  
17. Share/repost content with attribution to original poster

#### Network Building
18. Send connection requests to other personal users  
19. Accept or reject incoming connection requests  
20. View pending connection requests (sent and received)  
21. View my connections list  
22. Remove connections  
23. Follow business accounts and creator accounts  
24. Unfollow accounts  
25. View followers list  
26. View following list

#### Notifications
27. Receive in-app notifications for: connection requests, accepted connections, new followers, likes, comments, shares  
28. View unread notification count  
29. Mark notifications as read  
30. View notification history  
31. Manage notification preferences (enable/disable by type)

#### Feed & Discovery
32. View personalized feed (connections + followed accounts + own posts)  
33. View trending posts/hashtags  
34. Search posts by hashtags  
35. Filter feed by post type or user type

---

### Creator / Business Account User
> Creator and Business accounts include all Personal User features plus:

#### Enhanced Profile Management
1. Register as a creator or business account  
2. Create enhanced profile (name, category/industry, detailed bio, contact info, website + social media links)  
3. Add business address (business accounts)  
4. Add business hours (business accounts)  
5. Add multiple external links for endorsements/partnerships  
6. Showcase products/services (business accounts)

#### Advanced Content Creation
7. Create promotional posts  
8. Add call-to-action buttons (e.g., “Learn More”, “Shop Now”)  
9. Tag products/services in posts  
10. Schedule posts for future posting  
11. Pin important posts to profile
---

##  Architecture (Monolithic)
- Single application containing:
  - Authentication & Authorization (RBAC)
  - Profiles (Personal + Creator/Business)
  - Posts, Likes, Comments, Shares
  - Connections & Follows
  - Feed & Discovery
  - Notifications (real-time + history + preferences)

---

## Tech Stack (edit if different)
- **Backend:** Java + Spring Boot (REST APIs), Spring Security, JWT  
- **Database:** MySQL (or PostgreSQL)  
- **Frontend:** Angular  
- **Build Tools:** Maven (backend), Angular CLI (frontend)
---

##  Prerequisites
Make sure you have these installed:
- Java 17+ (or your project’s Java version)
- Maven 3.8+
- SpringBoot , JWT, Bcrypt
- Angular, Icons,
- Git
---

## Project Design
<img width="1920" height="1080" alt="Image" src="https://github.com/user-attachments/assets/d2808fc2-7acf-4206-8e0a-c4e1c5d28fbd" />
<img width="1920" height="1080" alt="Image" src="https://github.com/user-attachments/assets/b7abd9c4-5078-46a2-86e1-de3733fbef98" />
<img width="1920" height="1080" alt="Image" src="https://github.com/user-attachments/assets/ef9ff1f9-718f-450d-bcc8-fc84c05d3411" />
<img width="1920" height="1080" alt="Image" src="https://github.com/user-attachments/assets/855ed9c3-868d-42bd-967b-ae21c7982bd3" />

## Entity relationship diagram

