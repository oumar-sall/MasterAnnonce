package com.example.tp01dev.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AnnonceDTO {
    private Long id;

    @NotNull(message = "title is required")
    @Size(min = 3, max = 64)
    private String title;

    private String description;
    private String address;

    @Email(message = "mail format is invalid")
    private String mail;

    private String status;
    private Long categoryId;
    private String categoryLabel;
    private Long authorId;

    // Pattern Builder
    public static class Builder {
        private AnnonceDTO dto = new AnnonceDTO();

        public Builder id(Long id) { dto.id = id; return this; }
        public Builder title(String t) { dto.title = t; return this; }
        public Builder description(String d) { dto.description = d; return this; }
        public Builder adress(String a) { dto.address = a; return this; }
        public Builder mail(String m) { dto.mail = m; return this; }
        public Builder status(String s) { dto.status = s; return this; }
        public Builder categoryId(Long id) { dto.categoryId = id; return this; }
        public Builder categoryLabel(String l) { dto.categoryLabel = l; return this; }
        public Builder authorId(Long id) { dto.authorId = id; return this; }
        public AnnonceDTO build() { return dto; }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryLabel() {
        return categoryLabel;
    }

    public void setCategoryLabel(String categoryLabel) {
        this.categoryLabel = categoryLabel;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
