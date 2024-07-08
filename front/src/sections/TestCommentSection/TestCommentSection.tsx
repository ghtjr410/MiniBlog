import React, { useState } from 'react';
import TestLabel from 'components/TestLabel/TestLabel';

type Comment = {
  commentId: number;
  nickname: string;
  content: string;
  createdAt: string;
  isOwner: boolean;
};

type TestCommentSectionProps = {
  comments: Comment[];
  onAddComment: (content: string) => void;
  isUserLoggedIn: boolean;
  onLoginRequest: () => void;
  myNickname: string;
  onEditClick: (commentId: number, content: string) => void;
  onDeleteClick: (commentId: number) => void;
};

const TestCommentSection: React.FC<TestCommentSectionProps> = ({
  comments,
  onAddComment,
  isUserLoggedIn,
  onLoginRequest,
  myNickname,
  onEditClick,
  onDeleteClick,
}) => {
  const [newComment, setNewComment] = useState('');
  const [editCommentId, setEditCommentId] = useState<number | null>(null);
  const [editCommentContent, setEditCommentContent] = useState('');

  const handleAddComment = () => {
    if (newComment.trim()) {
      onAddComment(newComment);
      setNewComment('');
    }
  };

  const handleEditComment = (commentId: number, content: string) => {
    setEditCommentId(commentId);
    setEditCommentContent(content);
  };

  const handleUpdateComment = () => {
    if (editCommentContent.trim() && editCommentId !== null) {
      onEditClick(editCommentId, editCommentContent);
      setEditCommentId(null);
      setEditCommentContent('');
    }
  };

  const handleCancelEdit = () => {
    setEditCommentId(null);
    setEditCommentContent('');
  };

  return (
    <div className="w-full p-4 rounded-md bg-white shadow-md">
      <div className="mb-4">
        <textarea
          className="w-full p-2 border rounded-md resize-none"
          value={newComment}
          onChange={(e) => setNewComment(e.target.value)}
          placeholder="댓글을 입력하세요..."
          rows={1}
          onKeyPress={(e) => {
            if (e.key === 'Enter' && !e.shiftKey) {
              e.preventDefault();
              handleAddComment();
            }
          }}
          onInput={(e) => {
            e.currentTarget.style.height = 'auto';
            e.currentTarget.style.height = `${e.currentTarget.scrollHeight}px`;
          }}
        />
        <button
          className="mt-2 px-4 py-2 bg-blue-500 text-white rounded-md"
          onClick={isUserLoggedIn ? handleAddComment : onLoginRequest}
        >
          댓글 추가
        </button>
      </div>
      <div>
        {comments.map((comment) => (
          <div key={comment.commentId} className="p-2 border-b last:border-none">
            {editCommentId === comment.commentId ? (
              <div>
                <textarea
                  className="w-full p-2 border rounded-md resize-none"
                  value={editCommentContent}
                  onChange={(e) => setEditCommentContent(e.target.value)}
                />
                <button
                  className="mt-2 px-4 py-2 bg-green-500 text-white rounded-md"
                  onClick={handleUpdateComment}
                >
                  댓글 수정
                </button>
                <button
                  className="mt-2 px-4 py-2 bg-gray-500 text-white rounded-md"
                  onClick={handleCancelEdit}
                >
                  취소
                </button>
              </div>
            ) : (
              <div>
                <TestLabel text={comment.nickname} />
                <p className="whitespace-pre-wrap">{comment.content}</p>
                <span className="text-xs text-gray-500">{new Date(comment.createdAt).toLocaleString()}</span>
                {comment.nickname === myNickname && (
                  <div className="flex gap-2 mt-2">
                    <button
                      className="px-2 py-1 text-sm text-white bg-yellow-500 rounded"
                      onClick={() => handleEditComment(comment.commentId, comment.content)}
                    >
                      수정
                    </button>
                    <button
                      className="px-2 py-1 text-sm text-white bg-red-500 rounded"
                      onClick={() => onDeleteClick(comment.commentId)}
                    >
                      삭제
                    </button>
                  </div>
                )}
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
};

export default TestCommentSection;
