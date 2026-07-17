import { Component } from '@angular/core';

@Component({
  selector: 'app-user-profile',
  template: `
    <div class="profile-dashboard">
      <!-- 1. FORM EDITOR PANEL -->
      <div class="form-panel">
        <h3>Edit Profile</h3>
        
        <div class="form-group">
          <label>Username</label>
          <!-- TODO: Add two-way data binding for 'username'. Bind 'disabled' to 'isFormLocked' -->
          <input type="text" placeholder="Enter username">
        </div>

        <div class="form-group">
          <label>Bio Details</label>
          <!-- TODO: Add two-way data binding for 'bio'. Bind 'disabled' to 'isFormLocked' -->
          <textarea placeholder="Tell us about yourself..."></textarea>
        </div>

        <div class="form-group">
          <label>Avatar Endpoint URL</label>
          <!-- TODO: Add two-way data binding for 'avatarUrl'. Bind 'disabled' to 'isFormLocked' -->
          <input type="text" placeholder="Avatar URL">
        </div>

        <div class="actions">
          <!-- TODO: Bind button clicks to their respective handler methods -->
          <button class="btn btn-lock">Toggle Lock State</button>
          <button class="btn btn-reset">Clear Values</button>
        </div>
      </div>

      <!-- 2. REAL-TIME PREVIEW PANEL -->
      <div class="preview-panel">
        <h3>Live Preview</h3>
        <div class="card">
          <!-- TODO: Bind image src property to 'avatarUrl' -->
          <img class="avatar" alt="User Avatar">
          
          <!-- TODO: Render 'username' using Interpolation -->
          <h4 class="name">Username Placeholder</h4>
          
          <!-- TODO: Render 'bio' using Interpolation -->
          <p class="bio">Bio Placeholder</p>
        </div>
      </div>
    </div>
  `
})
export class UserProfileComponent {
  // TODO: Declare properties: username, bio, avatarUrl, isFormLocked
  
  // TODO: Implement constructor and initialization defaults

  // TODO: Implement handler methods: toggleFormLock() and resetForm()
}
