<!-- src/components/Navbar.vue -->
<template>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark border-bottom border-secondary shadow-sm">
      <div class="container">
        <router-link class="navbar-brand fw-bold text-white" to="/">DevFlow</router-link>
  
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
  
        <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
          <ul class="navbar-nav">
            <li class="nav-item">
              <router-link class="nav-link" to="/">Home</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/login">Login</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/register">Register</router-link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  </template>
  
  <style scoped>
  .nav-link {
    color: #ccc;
  }
  .nav-link:hover {
    color: #fff;
  }
  </style>
  