import UserBLogCardContent from "components/UserBlogComponent/UserBlogCardContent/UserBlogCardContent";
import UserBlogCardTitle from "components/UserBlogComponent/UserBlogCardTitle/UserBlogCardTitle";
import UserBlogCardBottomSection from "sections/UserBlogCardBottomSection/UserBlogCardBottomSection";

type UserBlogFormProps = {
    title: string;
    content: string;
    createdAt: string;
    commentCount: number;
    likeCount: number;
    onClick: () => void;
};

const UserBlogCardForm = ({
    title,
    content,
    createdAt,
    commentCount,
    likeCount,
    onClick,
}: UserBlogFormProps) => {
    return(
        <div className="h-60 border rounded shadow-xl p-4 mb-10 hover:shadow-custom hover:scale-105 cursor-pointer bg-white flex flex-col justify-between">
            <div>
                <UserBlogCardTitle title={title} onClick={onClick} />
                <UserBLogCardContent content={content} onClick={onClick} />
            </div>
            <UserBlogCardBottomSection 
                createdAt={createdAt}
                commentCount={commentCount}
                likeCount={likeCount}
            />
        </div>
    );
};

export default UserBlogCardForm;