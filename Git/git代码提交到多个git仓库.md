### һ��������������Ӳֿ⣺
1. ����ӵ�һ��GitHub�Ĳֿ��ַ��
`Git remote add origin https://github.com/huangge1199/my-blog.Git`
2. �����gitee�Ĳֿ��ַ
`Git remote set-url --add origin https://gitee.com/huangge1199_admin/my-blog.Git`
3. �鿴Զ�ֿ̲��ַ�Ƿ���ӳɹ���
`Git remote -v`
4. ���push�ύ���� �����Ļ�����pushʱ���ͻὫ����ͬʱ���͵������ֿ��ˡ�
`Git push origin master -f`

### ����ֱ���޸���ĿĿ¼������Ŀ¼.git�е�config�ļ�����[remote ��origin��]����Ӷ���ֿ��ַ�Ϳ����ˣ��ο����£�
```
[remote "origin"]
url = https://gitee.com/huangge1199_admin/my-blog.Git
fetch = +refs/heads/*:refs/remotes/origin/*
url = https://github.com/huangge1199/my-blog.Git

