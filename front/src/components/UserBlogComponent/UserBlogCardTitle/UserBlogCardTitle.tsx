type UserBlogCardTitleProps = {
    title: string;
    onClick: () => void;
};

const UserBlogCardTitle = ({ title, onClick }: UserBlogCardTitleProps) => {
    return (
        <h4 className="font-bold mb-8 text-xl pb-6 overflow-hidden line-clamp-1 h-3" onClick={onClick}>
            {title}
        </h4>

    );            
};
export default UserBlogCardTitle;