import React from 'react';
import TestLabel from 'components/TestLabel/TestLabel';
import { AiOutlineHeart, AiFillHeart } from 'react-icons/ai';

type TestUserInfoSectionProps = {
  nickname: string;
  date: string;
  likeCount: string;
  isOwner: boolean;
  onLikeClick: () => void;
  hasLiked: boolean;
  onDeleteClick: () => void; // 추가된 prop
  onEditClick: () => void; // 추가된 prop
};

const TestUserInfoSection: React.FC<TestUserInfoSectionProps> = ({ nickname, date, likeCount, isOwner, onLikeClick, hasLiked, onDeleteClick, onEditClick  }) => {
  return (
    <div className="w-full flex justify-between">
      <div className='flex gap-4'>
        <div className="p-3 rounded-md bg-white shadow-md">
          <TestLabel text={nickname} />
        </div>
        <div className="p-3 rounded-md bg-white shadow-md">
          <TestLabel text={date} />
        </div>
        <div className="ml-auto p-3 rounded-md bg-white shadow-md cursor-pointer flex items-center" onClick={onLikeClick}>
          {hasLiked ? <AiFillHeart className="text-xl text-red-500" /> : <AiOutlineHeart className="text-xl text-gray-500" />}
          <span className="ml-1 text-l">
            {likeCount}
          </span>
        </div>
      </div>
      
      {isOwner && (
        <div className="flex gap-4 justify-end items-end">
          <div className="ml-auto p-3 rounded-md bg-white shadow-md cursor-pointer text-sm" onClick={onEditClick}>
            수정
          </div>
          <div className="ml-auto p-3 rounded-md bg-white shadow-md cursor-pointer text-sm" onClick={onDeleteClick}>
            삭제
          </div>
        </div>
      )}
    </div>
  );
};

export default TestUserInfoSection;
