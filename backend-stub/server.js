// FILE: CommunityApp/backend-stub/server.js
const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const { v4: uuidv4 } = require('uuid');

const app = express();
const PORT = 3000;

app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

let posts = [
  {
    id: 1,
    title: "Welcome to Bahai Community",
    content: "This is a sample post to demonstrate the app functionality. Connect with fellow Bahá'ís and share your thoughts!",
    authorName: "Admin",
    authorId: "admin-1",
    imageUrl: null,
    createdAt: new Date().toISOString(),
    updatedAt: new Date().toISOString()
  },
  {
    id: 2,
    title: "Daily Meditation Reflection",
    content: "Today I reflect on the beauty of unity in diversity that the Bahá'í Faith teaches us. How wonderful it is to see people from all walks of life coming together!",
    authorName: "Sarah Johnson",
    authorId: "user-1",
    imageUrl: null,
    createdAt: new Date(Date.now() - 86400000).toISOString(),
    updatedAt: new Date(Date.now() - 86400000).toISOString()
  },
  {
    id: 3,
    title: "Service Project Update",
    content: "Our community service project is making great progress! We've successfully distributed food packages to 50 families in need. Thank you to all volunteers!",
    authorName: "Ahmed Hassan",
    authorId: "user-2",
    imageUrl: null,
    createdAt: new Date(Date.now() - 172800000).toISOString(),
    updatedAt: new Date(Date.now() - 172800000).toISOString()
  }
];

let users = [
  {
    id: "user-1",
    name: "Sarah Johnson",
    email: "sarah@example.com",
    profileImageUrl: null,
    bio: "Passionate about Bahá'í teachings and community service."
  },
  {
    id: "user-2", 
    name: "Ahmed Hassan",
    email: "ahmed@example.com",
    profileImageUrl: null,
    bio: "Youth coordinator and enthusiastic about teaching the Faith."
  },
  {
    id: "admin-1",
    name: "Admin User",
    email: "admin@example.com", 
    profileImageUrl: null,
    bio: "Community administrator."
  }
];

let nextPostId = 4;

app.get('/posts', (req, res) => {
  res.json(posts.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt)));
});

app.get('/posts/:id', (req, res) => {
  const post = posts.find(p => p.id === parseInt(req.params.id));
  if (post) {
    res.json(post);
  } else {
    res.status(404).json({ error: 'Post not found' });
  }
});

app.post('/posts', (req, res) => {
  const { title, content, imageUrl } = req.body;
  
  if (!title || !content) {
    return res.status(400).json({ error: 'Title and content are required' });
  }
  
  const newPost = {
    id: nextPostId++,
    title,
    content,
    authorName: "Current User",
    authorId: "current-user",
    imageUrl: imageUrl || null,
    createdAt: new Date().toISOString(),
    updatedAt: new Date().toISOString()
  };
  
  posts.push(newPost);
  res.status(201).json(newPost);
});

app.post('/posts/upload', (req, res) => {
  const { title, content } = req.body;
  
  if (!title || !content) {
    return res.status(400).json({ error: 'Title and content are required' });
  }
  
  const newPost = {
    id: nextPostId++,
    title,
    content,
    authorName: "Current User",
    authorId: "current-user",
    imageUrl: "https://via.placeholder.com/400x200?text=Uploaded+Image",
    createdAt: new Date().toISOString(),
    updatedAt: new Date().toISOString()
  };
  
  posts.push(newPost);
  res.status(201).json(newPost);
});

app.post('/auth/login', (req, res) => {
  const { email, password } = req.body;
  
  if (!email || !password) {
    return res.status(400).json({ error: 'Email and password are required' });
  }
  
  const user = users.find(u => u.email === email) || {
    id: "demo-user",
    name: "Demo User",
    email: email,
    profileImageUrl: null,
    bio: "This is a demo user account."
  };
  
  const token = "mock-jwt-token-" + uuidv4();
  
  res.json({
    token,
    user
  });
});

app.get('/users/:id', (req, res) => {
  const user = users.find(u => u.id === req.params.id);
  if (user) {
    res.json(user);
  } else {
    res.status(404).json({ error: 'User not found' });
  }
});

app.get('/health', (req, res) => {
  res.json({ status: 'OK', message: 'Bahai Community Backend is running!' });
});

app.listen(PORT, () => {
  console.log(`Bahai Community Backend running on http://localhost:${PORT}`);
  console.log('Available endpoints:');
  console.log('  GET  /posts');
  console.log('  GET  /posts/:id');
  console.log('  POST /posts');
  console.log('  POST /posts/upload');
  console.log('  POST /auth/login');
  console.log('  GET  /users/:id');
  console.log('  GET  /health');
});

module.exports = app;
