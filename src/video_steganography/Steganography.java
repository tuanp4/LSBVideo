package video_steganography;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Valdez
 */
public class Steganography {

    private final String VIDEO_START_SEQUENCE = "movi";
    private final String END_SEQUENCE = "\u0000";

    /**
     * Method, which writes passed byte array into a file specified by given
     * path.
     *
     * @param fileContent	byte array that will be saved into the file
     * @param fileName	path of the output file
     */
    public void writeFile(byte[] fileContent, String fileName) throws FileNotFoundException, IOException {
        File file = new File(fileName);
        BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
        output.write(fileContent);
        output.close();
    }

    /**
     * Method, which goal is to hide the message into the byte array - one bit
     * of the message per one byte from the array. It first calls
     * {@code getVideoStreamPos} method that tries to find the start of the
     * video stream from the file ('movi' list) and return the number of
     * relevant byte. The position is then moved by 4 bytes forward, where
     * should be first RIFF chunk with the stream data. Method then loops
     * through individual chunks until there are no more chunks or the message
     * was completely hidden in the data. The resulting byte array is then
     * returned.
     *
     * @param message	message to be hidden in the data
     * @param fileContent	byte array from the input file
     * @return	modified byte array with hidden message
     */
    public byte[] hideMessage(String message, byte[] fileContent) throws ArrayIndexOutOfBoundsException {
        int currentPos = 0;

        currentPos = getVideoStreamPos(fileContent);
//            System.out.println("Video stream (movi list) found at " + currentPos + ". byte.");
        currentPos += 4; //moving to the first chunk

        String messageToHide = message + END_SEQUENCE;
        int chunkSize;
        while ((chunkSize = getNextChunkSize(fileContent, currentPos)) > 0 && messageToHide != null) {
            currentPos += 8; //moving to chunk's data
            messageToHide = saveMessageIntoChunk(messageToHide, fileContent, currentPos, chunkSize);
            currentPos += chunkSize;
        }
        return fileContent;
    }

    /**
     * Method, which searches the file content for the hidden message. It first
     * finds the start of first video stream with method
     * {@code getVideoStreamPos}. If it is found, this method loops through its
     * individual chunks and with method {@code getMessageFromChunk} extracting
     * parts of the message into a String that is returned from this method.
     * Message is finished, when specified end sequence is found.
     *
     * @param fileContent	byte arrays with the file content
     * @return	String with the hidden message
     */
    public String findMessage(byte[] fileContent) throws ArrayIndexOutOfBoundsException {
        String message = "";
        int currentPos = 0;

        currentPos = getVideoStreamPos(fileContent);
        currentPos += 4; //moving to the first chunk

        int chunkSize;
        while ((chunkSize = getNextChunkSize(fileContent, currentPos)) > 0) {
            currentPos += 8; //moving to chunk's data
            message = getMessageFromChunk(message, fileContent, currentPos, chunkSize);
            currentPos += chunkSize;
            if (message.substring(message.length() - END_SEQUENCE.length()).equals(END_SEQUENCE)) {
                break;
            }
        }

        return message.substring(0, message.length() - END_SEQUENCE.length());
    }

    /**
     * Method, which cycles through the bytes of the passed array in effort to
     * find 'movi' sequence, which means beginning of the video stream from AVI
     * file. Once the sequence is found, the index of the first byte of the
     * sequence is returned.
     *
     * @param content	byte array with file content
     * @return	position of the first byte of the 'movi' sequence.
     * @throws ArrayIndexOutOfBoundsException	in case that 'movi' sequence won't
     * be found, the while loop will eventually increase the index out of bounds
     * of the byte array, which also means the input file is probably not AVI
     */
    public int getVideoStreamPos(byte[] content) throws ArrayIndexOutOfBoundsException {
        int bytePos = -1;
        String valueFound = "";

        while (!valueFound.equals(VIDEO_START_SEQUENCE)) {
            bytePos++;
            valueFound = "";
            for (int i = bytePos; i < bytePos + 4; i++) {
                valueFound += (char) content[i];
            }
        }

        return bytePos;
    }

    /**
     * Method for finding the size of one chunk in bytes. The 4 bytes containing
     * the size value are extracted and this value is processed into an Integer,
     * this Integer is then returned.
     *
     * @param content	byte array with file content
     * @param chunkPos	the index of a byte, from which the specified chunk
     * starts
     * @return	Integer with a size of the chunk
     */
    public int getNextChunkSize(byte[] content, int chunkPos) {
        int chunkSize = 0;

        int currPos = chunkPos + 4;
        byte[] sizeBytes = new byte[4];

        for (int i = sizeBytes.length - 1; i >= 0; i--) {
            sizeBytes[i] = content[currPos];
            currPos++;
        }

        ByteBuffer sizeBytesWrapper = ByteBuffer.wrap(sizeBytes);
        chunkSize = sizeBytesWrapper.getInt();

        return chunkSize;
    }

    /**
     * Method, which hides individual characters of message into a content of a
     * chunk specified by its position and size. It loops through the bytes of
     * the chunk (the limit is padded to 8-byte parts) and if there's still some
     * message to be hidden, if extract its next character and by LSB method
     * hides its bits into chunk's bytes. If the message bits count exceeds the
     * size of the chunk, the rest of the message is returned from the method
     * for the next chunk.
     *
     * @param message	message or part of the message to be hidden into chunk
     * @param content	byte arrays with file content
     * @param pos	starting position of the chunk's content
     * @param chunkSize	size of the chunk in bytes
     * @return	String with the rest of the message that couldn't be hidden (it
     * is null, if the entire message was hidden)
     */
    public String saveMessageIntoChunk(String message, byte[] content, int pos, int chunkSize) {
        String remainingMessage = null;
        int messageIndex = 0;
        int charIndex = 0;
        int currPos = pos;
        int overhead = chunkSize % 8;

        for (int i = 0; i < (chunkSize - overhead); i++) {
            if (messageIndex < message.length()) {
                char messageChar = message.charAt(messageIndex);
                int bit_ch = (messageChar >> charIndex) & 1;
                content[currPos] = (byte) ((content[currPos] & (byte) 0xFE) | (byte) bit_ch);
                currPos++;

                if (charIndex >= 7) {
                    charIndex = 0;
                    messageIndex++;
                } else {
                    charIndex++;
                }
            } else {
                break;
            }
        }

        if (messageIndex < message.length()) {
            remainingMessage = message.substring(messageIndex);
        }

        return remainingMessage;
    }

    /**
     * Method, which picks individual bits of the message from chunk's bytes
     * (LSB method of steganography). It loops through individual bytes of the
     * chunk and extracts the bits, which are then combined into individual
     * characters of the message. When message has been completed (or the loop
     * reached the end of the chunk), the resulting String is returned from the
     * method.
     *
     * @param message	part of the message from earlier chunk
     * @param content	byte arrays with file content
     * @param pos	starting position of the chunk's content
     * @param chunkSize	size of the chunk in bytes
     * @return String with the hidden message or its part
     */
    public String getMessageFromChunk(String message, byte[] content, int pos, int chunkSize) {
        String resultMessage = message;
        int currPos = pos;
        int overhead = chunkSize % 8;
        int charIndex = 0;
        char messageChar = 0;

        for (int i = 0; i < (chunkSize - overhead); i++) {
            int bit_byte = (content[currPos] & (byte) 0x01) << charIndex;
            messageChar = (char) (messageChar | (char) bit_byte);

            currPos++;

            if (charIndex >= 7) {
                resultMessage += messageChar;
                messageChar = 0;
                charIndex = 0;

                if (resultMessage.length() >= 4) {
                    if (resultMessage.substring(resultMessage.length() - END_SEQUENCE.length()).equals(END_SEQUENCE)) {
                        break;
                    }
                }
            } else {
                charIndex++;
            }
        }

        return resultMessage;
    }
}
