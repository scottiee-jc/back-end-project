package com.example.backEndProject.service;

import com.example.backEndProject.model.Post;
import com.example.backEndProject.model.User;
import com.example.backEndProject.repository.PostRepository;
import com.example.backEndProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Map;

@Service
public class PostService {


//    DEPENDENCY INJECTION

    @Autowired
    private UserRepository userRepository;

    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


//    END OF DEPENDENCY INJECTION
//
//
//    START OF METHODS


    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }


    public Post findPostByID(Long id){
        return postRepository.findPostByID(id);
    }


    public int findLikesByID(Long id) {

        Post current = null;
        try {
            current = postRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            System.out.println("No matching post could be found for id: " + id);
        }

        return current.getNumber_of_likes();
    }


    public List searchPostsForKeyword(String keyword) {

//        Returns the posts that contain the relevant keyword

        return postRepository.findAll().stream()
                .map(Post::getContent_text)
                .filter(s -> s.contains(keyword))
                .toList();
    }


    public Post updateLikeCount(Long id)
            throws NoSuchElementException {

//        Adds like to specific post by id.
//        Try catch statement for the scenario where id is not found.
//        Created current outside of try catch to ensure it was within scope for the return statement.

        Post current = null;
        try {
            current = postRepository.findById(id).get();
            current.setNumber_of_likes(current.getNumber_of_likes() + 1);
            postRepository.save(current);

        } catch (NoSuchElementException e) {
            e.printStackTrace();
            System.out.println("No matching post could be found for id: " + id);
        }

        return current;
    }



    public Post superLikePost(Long id)
            throws NoSuchElementException {

//        Same as above but with 2 likes added.
//        Adds like to specific post by id.
//        Try catch statement for the scenario where id is not found.
//        Created current outside of try catch to ensure it was within scope for the return statement.

        Post current = null;
        try {
            current = postRepository.findById(id).get();
            current.setNumber_of_likes(current.getNumber_of_likes() + 2);
            postRepository.save(current);

        } catch (NoSuchElementException e) {
            e.printStackTrace();
            System.out.println("No matching post could be found for id: " + id);
        }

        return current;
    }



    public Post editPost(Long id,
                         String new_content)
            throws NoSuchElementException, IOException {

//        Edits already established post by id.
//        Try catch statement for the scenario where id is not found.
//        Created current outside of try catch to ensure it was within scope for the return statement.

        Post current = null;
        try {
            current = postRepository.findById(id).get();
            current.setContent_text(new_content);
            postRepository.save(current);

        } catch (NoSuchElementException e) {
            e.printStackTrace();
            System.out.println("No matching post could be found for id: " + id);
        }

        File myFile = new File("src/all_posts_and_post_edits.txt");
        if (!myFile.exists()) {
            try {
                myFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        FileWriter fileWriter = new FileWriter(myFile, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(current.getContent_text());
        printWriter.println(LocalDateTime.now());
        printWriter.println(current.getUser().getName());
        printWriter.println("");

        printWriter.close();

        return current;
    }


    public String deletePostByID(Long id) {

        // deletes a specific post by the post id

        Post result = postRepository.findPostByID(id);
        postRepository.delete(result);

        return "Deleted";
    }


    public Post addPost(
            Long id,
            String content_text,
            Integer number_of_likes,
            boolean isBusinessAccount,
            Integer post_type_id,
            Long user_id) throws IOException {


        Post post = new Post(id, content_text, number_of_likes, isBusinessAccount, post_type_id);
        postRepository.save(post);

        File myFile = new File("src/all_posts_and_post_edits.txt");
        if (!myFile.exists()) {
            try {
                myFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        User postUser = userRepository.findByID(user_id);
        FileWriter fileWriter = new FileWriter(myFile, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(post.getContent_text());
        printWriter.println(LocalDateTime.now());
        printWriter.println(postUser.getName());
        printWriter.println("");

        printWriter.close();

        return postRepository.save(post);
    }






//    END OF METHODS
//
//
//    CODE IN PROGRESS



//    END OF CODE IN PROGRESS
//
//
//    END OF FILE
}
